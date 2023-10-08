package zsdev.work.lib.support.network.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import io.reactivex.rxjava3.observers.ResourceObserver;
import zsdev.work.lib.support.dialog.custom.DialogUserCustomView;
import zsdev.work.lib.support.network.INetworkHandler;
import zsdev.work.lib.support.network.exception.NetworkError;
import zsdev.work.lib.support.network.exception.ResponseThrowable;
import zsdev.work.lib.support.utils.LogUtil;
import zsdev.work.lib.support.utils.network.newnet.NetworkLollipopAfterUtil;


/**
 * Created: by 2023-09-12 00:40
 * Description: 描述同BaseFlowableSubscriber
 * Author: 张松
 */
public abstract class BaseObserverSubscriber<T> extends ResourceObserver<T> implements INetworkHandler<T> {

    /**
     * Context上下文
     */
    private final Context context;

    /**
     * 请求等待框
     */
    private final Dialog dialog;

    /**
     * 创建BaseSubscriber 用来接收上下文，设置网络请求进度条
     *
     * @param context  Context上下文
     * @param activity Activity上下文
     */
    public BaseObserverSubscriber(Context context, Activity activity) {
        LogUtil.i("BaseObserverSubscriber", "BaseObserverNormalSubscriber():" + context.getClass().getSimpleName());
        this.context = context;
        dialog = DialogUserCustomView.setFirewoodLoadingDialog(activity);
    }

    /**
     * 创建BaseSubscriber 用来接收上下文，设置网络请求进度条
     *
     * @param context           Context上下文
     * @param activity          Activity上下文
     * @param loadingRendererId LoadingRenderer的id
     */
    public BaseObserverSubscriber(Context context, Activity activity, int loadingRendererId) {
        LogUtil.i("BaseObserverSubscriber", "BaseObserverNormalSubscriber():" + context.getClass().getSimpleName());
        this.context = context;
        dialog = DialogUserCustomView.setModeLoadingDialogRendererId(activity, loadingRendererId);
    }

    /**
     * 网络请求开始
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("BaseObserverSubscriber", "onStart():显示进度条");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!NetworkLollipopAfterUtil.isNetAvailable(context)) {
                LogUtil.i("BaseObserverSubscriber", "onStart():当前网络不可用，请检查网络情况");
                // 一定好主动调用下面这一句
                onComplete();
            }
        }
        //显示等待框
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 网络请求错误
     *
     * @param throwable 异常对象
     */
    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof ResponseThrowable) {
            onFail((ResponseThrowable) throwable);
            LogUtil.i("BaseObserverSubscriber", "onError()错误码:" + ((ResponseThrowable) throwable).getCode());
            LogUtil.i("BaseObserverSubscriber", "onError()错误原因:" + ((ResponseThrowable) throwable).getMsg());
        } else {
            onFail(new ResponseThrowable(throwable, NetworkError.UNKNOWN));
            LogUtil.i("BaseObserverSubscriber", "onError():其他错误");
        }
        //关闭等待框
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 网络请求成功将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型实体
     */
    @Override
    public void onNext(T t) {
        LogUtil.i("BaseObserverSubscriber", "onNext()：" + t.toString());
        onSuccess(t);
    }

    /**
     * 请求或响应完结不论成功与失败
     * 完成隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        LogUtil.i("BaseObserverSubscriber", "onComplete():关闭等待框");
        //关闭等待框
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
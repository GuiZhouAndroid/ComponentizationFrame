package zsdev.work.lib.support.network.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import io.reactivex.rxjava3.subscribers.ResourceSubscriber;
import zsdev.work.lib.support.dialog.custom.DialogUserCustomView;
import zsdev.work.lib.support.network.INetworkHandler;
import zsdev.work.lib.support.network.exception.NetworkError;
import zsdev.work.lib.support.network.exception.ResponseThrowable;
import zsdev.work.lib.support.utils.LogUtil;
import zsdev.work.lib.support.utils.network.newnet.NetworkLollipopAfterUtil;


/**
 * Created: by 2023-09-07 23:48
 * Description:统一管理Subscriber订阅处理返回数据
 * 自定义一个Subscriber来对Exception进行捕获，
 * 也需要对其它Exception进行捕获和包裹，防止发生错误后直接崩溃。
 * Subscriber基类,可以在这里处理client网络连接状况（比如没有wifi，没有4g，没有联网等）
 * Author: 张松
 */
public abstract class BaseFlowableSubscriber<T> extends ResourceSubscriber<T> implements INetworkHandler<T> {

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
    public BaseFlowableSubscriber(Context context, Activity activity) {
        LogUtil.i("BaseFlowableSubscriber", "BaseFlowableNormalSubscriber():" + context.getClass().getSimpleName());
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
    public BaseFlowableSubscriber(Context context, Activity activity, int loadingRendererId) {
        LogUtil.i("BaseFlowableSubscriber", "BaseFlowableNormalSubscriber():" + context.getClass().getSimpleName());
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
        LogUtil.i("BaseFlowableSubscriber", "onStart():显示进度条");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!NetworkLollipopAfterUtil.isNetAvailable(context)) {
                LogUtil.i("BaseFlowableSubscriber", "onStart():当前网络不可用，请检查网络情况");
                // 必须调用，意味着本次请求彻底结束
                onComplete();
            }
        }
        //显示等待框
        if (dialog != null) {
            dialog.show();
        }
        // 此处开始调用okhttp拦截器借助RxJava调度对应线程开始请求与响应
        // 如请求成功就回调onNext(成功数据)，如请求失败就回调onError(Throwable)，可以对此Throwable进行instanceof判定类型返回响应失败原因UI显示
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
            LogUtil.i("BaseFlowableSubscriber", "onError()错误码:" + ((ResponseThrowable) throwable).getCode());
            LogUtil.i("BaseFlowableSubscriber", "onError()错误原因:" + ((ResponseThrowable) throwable).getMsg());
        } else {
            onFail(new ResponseThrowable(throwable, NetworkError.UNKNOWN));
            LogUtil.i("BaseFlowableSubscriber", "onError():其他错误");
        }
        //关闭等待框
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 网络请求成功
     * 将继续使用onNext方法处理返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型实体
     */
    @Override
    public void onNext(T t) {
        LogUtil.i("BaseFlowableSubscriber", "onNext()：" + t.toString());
        onSuccess(t);
    }

    /**
     * 请求或响应完成不论成功与失败
     * 完成隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        LogUtil.i("BaseFlowableSubscriber", "onComplete():关闭等待框");
        //关闭等待框
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}

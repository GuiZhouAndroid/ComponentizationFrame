package zsdev.work.module.support.test.app.presenter;


import zsdev.work.lib.support.mvp.BaseActivity;
import zsdev.work.lib.support.mvp.BasePresenter;
import zsdev.work.lib.support.network.base.BaseFlowableSubscriber;
import zsdev.work.lib.support.network.exception.ResponseThrowable;
import zsdev.work.lib.support.network.rxjava.transformer.HandlerTransformer;
import zsdev.work.lib.support.utils.LogUtil;
import zsdev.work.module.support.test.app.App;
import zsdev.work.module.support.test.app.bean.My;
import zsdev.work.module.support.test.app.contract.MainContract;
import zsdev.work.module.support.test.app.model.MainModel;

/**
 * Created: by 2023-09-26 18:31
 * Description:
 * Author: 张松
 */
public class MainPresenter extends BasePresenter<MainContract.View, MainContract.Model> implements MainContract.Presenter {

    // TODO: 2023/10/11 待重构new对象，引用hilt注入优化
    MainModel mainModel = new MainModel();

    public MainPresenter(MainContract.View nowView) {
        super(nowView);
        LogUtil.i(TAG, "MainPresenter()");
    }

    public MainPresenter(MainContract.View nowView, MainContract.Model nowModel) {
        super(nowView, nowModel);
        LogUtil.i(TAG, "MainPresenter()");
    }

    @Override
    public void login(String username, String password) {

    }

    /**
     * 实现MainContract.Presenter 接口中的 login(String username, String password) 方法
     */
    @Override
    public void login2() {
        //循环发送数字 测试内存泄漏
//        mainModel.login2().interval(1, TimeUnit.SECONDS)
//                .compose(SchedulerTransformer.getFlowableScheduler())
//                .to(bindLifecycle())//加上这行代码，视图关闭调用onDestroy()定时结束，反之继续打印aLong值
//                .subscribe(new DisposableSubscriber<Long>() {
//            @Override
//            public void onNext(Long aLong) {
//                System.out.println("aLong = " + aLong);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
        HandlerTransformer.handlerSubscribe(mainModel.login2(), bindLifecycle(), new BaseFlowableSubscriber<My>(App.getContext(), BaseActivity.getActivity(), 9) {

            @Override
            public void onFail(ResponseThrowable responseThrowable) {
                nowView.loginFailure(responseThrowable.getCode() + ":" + responseThrowable.getMsg());
            }

            @Override
            public void onSuccess(My my) {
                // nowView.loginSuccess(my);
                nowView.onSuccessNetUI(my.getName());
            }
        });
    }
}


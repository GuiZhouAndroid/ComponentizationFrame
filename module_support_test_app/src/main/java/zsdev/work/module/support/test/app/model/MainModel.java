package zsdev.work.module.support.test.app.model;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import zsdev.work.lib.support.network.base.BaseResponse;
import zsdev.work.module.support.test.app.ServiceBuildHelper;
import zsdev.work.module.support.test.app.bean.LoginBean;
import zsdev.work.module.support.test.app.bean.My;
import zsdev.work.module.support.test.app.contract.MainContract;

/**
 * Created: by 2023-09-26 18:30
 * Description:
 * Author: 张松
 */
public class MainModel implements MainContract.Model {

    @Override
    public Observable<LoginBean> login(String username, String password) {
        return ServiceBuildHelper.getApiService().login(username, password);
    }

    @Override
    public Flowable<BaseResponse<My>> login2() {
        return ServiceBuildHelper.getApiService().login2();
    }

    @Override
    public void onDestroy() {

    }
}

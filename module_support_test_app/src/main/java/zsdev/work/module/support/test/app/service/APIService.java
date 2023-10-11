package zsdev.work.module.support.test.app.service;


import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zsdev.work.lib.support.network.base.BaseApiService;
import zsdev.work.lib.support.network.base.BaseResponse;
import zsdev.work.module.support.test.app.bean.LoginBean;
import zsdev.work.module.support.test.app.bean.My;

/**
 * Created: by 2023-09-26 18:30
 * Description:
 * Author: 张松
 */
public interface APIService extends BaseApiService {

    /**
     * 登陆
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

    @POST("/network/error")
    Flowable<BaseResponse<My>> login2();

}

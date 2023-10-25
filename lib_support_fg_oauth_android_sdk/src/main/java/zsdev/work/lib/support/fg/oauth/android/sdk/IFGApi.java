package zsdev.work.lib.support.fg.oauth.android.sdk;

/**
 * Created: by 2023-10-18 13:52
 * Description: 提供外部调用接口
 * Author: 张松
 */
public interface IFGApi {

    /**
     * 是否安装胡贵APP
     *
     * @return true-已安装 false-未安装
     */
    boolean isFgAppInstalled();

    /**
     * 获取应用id
     *
     * @return appId
     */
    String getAppId();

    /**
     * 获取应用密钥
     *
     * @return secret
     */
    String getAppSecret();

    /**
     * 请求处理授权认证
     */
    void handleOAuthRequest();
}

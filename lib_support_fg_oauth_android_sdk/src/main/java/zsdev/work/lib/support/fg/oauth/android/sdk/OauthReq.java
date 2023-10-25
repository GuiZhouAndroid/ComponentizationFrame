package zsdev.work.lib.support.fg.oauth.android.sdk;

/**
 * Created: by 2023-10-25 12:50
 * Description: 授权请求实体
 * Author: 张松
 */
public class OauthReq {
    private String appId;
    private String appSecret;

    public OauthReq(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String toString() {
        return "OauthReq{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                '}';
    }
}

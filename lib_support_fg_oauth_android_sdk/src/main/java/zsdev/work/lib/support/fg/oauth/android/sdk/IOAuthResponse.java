package zsdev.work.lib.support.fg.oauth.android.sdk;

/**
 * Created: by 2023-10-24 20:08
 * Description: 回调接口，用于传递数据和回调code给调用方
 * Author: 张松
 */
public interface IOAuthResponse {

    /**
     * 授权回调Code实体数据
     *
     * @param oauthRep 授权Code数据
     */
    void callBackOauthResponse(OauthRep oauthRep);
}


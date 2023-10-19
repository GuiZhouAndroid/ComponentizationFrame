package zsdev.work.lib.support.fg.oauth.android.sdk;

/**
 * Created: by 2023-10-18 13:52
 * Description:
 * Author: 张松
 */
public interface IFGApi {
    boolean registerApp(String appId);

    boolean isFgAppInstalled();

    boolean sendReq();

    String getAppId();

    String getAppSecret();

    boolean handleIntent();

    boolean openFGApp();

}

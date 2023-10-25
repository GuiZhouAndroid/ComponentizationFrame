package zsdev.work.lib.support.fg.oauth.android.sdk;

import android.content.Context;

/**
 * Created: by 2023-10-18 13:46
 * Description: 注册API工厂
 * Author: 张松
 */
public class FGAPIFactory {

    //授权回调接口
    protected static IOAuthResponse mIOAuthResponse;

    private FGAPIFactory() {
        throw new RuntimeException(FGAPIFactory.class.getSimpleName() + "cannot instantiate!");
    }

    public static IFGApi createFGApi(Context context) {
        return new FGApiImpl(context);
    }

    /**
     * IOAuthResponse 接口的实例
     *
     * @param iOAuthResponse IOAuthResponse实例
     */
    public static void setOAuthResponseCallbackListener(IOAuthResponse iOAuthResponse) {
        mIOAuthResponse = iOAuthResponse;
    }
}

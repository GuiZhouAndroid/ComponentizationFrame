package zsdev.work.lib.support.fg.oauth.android.sdk;

import android.content.Context;

/**
 * Created: by 2023-10-18 13:46
 * Description: 注册API工厂
 * Author: 张松
 */
public class FGAPIFactory {

    private FGAPIFactory() {
        throw new RuntimeException(FGAPIFactory.class.getSimpleName() + "cannot instantiate!");
    }

    public static IFGApi createFGApi(Context context) {
        return new FGApiImpl(context);
    }

    public static ICallBack getCode() {
        return new CallBackImpl();
    }
}

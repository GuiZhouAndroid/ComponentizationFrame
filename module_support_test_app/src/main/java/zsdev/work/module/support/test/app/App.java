package zsdev.work.module.support.test.app;

import android.content.Context;

import zsdev.work.lib.support.mvp.base.BaseApplication;


/**
 * Created: by 2023-09-26 15:24
 * Description:
 * Author: 张松
 */
public class App extends BaseApplication {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}

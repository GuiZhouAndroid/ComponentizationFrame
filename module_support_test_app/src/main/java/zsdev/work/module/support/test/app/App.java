package zsdev.work.module.support.test.app;

import zsdev.work.lib.support.mvp.BaseApplication;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-09-26 15:24
 * Description:
 * Author: 张松
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setLogEnable(false);
    }
}

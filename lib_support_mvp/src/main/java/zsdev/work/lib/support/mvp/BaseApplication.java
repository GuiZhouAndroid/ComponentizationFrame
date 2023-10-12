package zsdev.work.lib.support.mvp;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import zsdev.work.lib.support.utils.ActivityManagerUtil;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-09-20 12:49
 * Description:基类Application：
 * 业务组件在集成模式下是不能有自己的Application的，但在组件开发模式下又必须实现自己的Application
 * 并且要继承自Common组件的BaseApplication并且这个Application不能被业务组件中的代码引用，
 * 因为它的功能就是为了使业务组件从BaseApplication中获取的全局Context生效，还有初始化数据之用。
 * Author: 张松
 */
public class BaseApplication extends MultiDexApplication {
    //全局的Application对象
    private static BaseApplication baseApplication;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志工具类
        LogUtil.setLogEnable(true, true);
        LogUtil.i("BaseApplication", "onCreate()");
        //绑定Application
        baseApplication = this;
        //绑定Context
        context = this.getApplicationContext();
    }

    /**
     * 全局Application
     *
     * @return BaseApplication单例
     */
    public static BaseApplication getApplication() {
        return baseApplication;
    }

    /**
     * 全局Context上下文
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }

    /**
     * Activity的管理，将Activity压入栈
     *
     * @return ActivityManagerUtil单例
     */
    public static ActivityManagerUtil getActivityManagerUtil() {
        return ActivityManagerUtil.getInstance();
    }
}

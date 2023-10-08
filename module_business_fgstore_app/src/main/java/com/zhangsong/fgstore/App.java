package com.zhangsong.fgstore;

import com.alibaba.android.arouter.launcher.ARouter;

import zsdev.work.lib.support.mvp.BaseApplication;
import zsdev.work.lib.support.utils.InitUtil;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-10-07 22:26
 * Description:
 * Author: 张松
 */
public class App extends BaseApplication {

    /**
     * 上下文
     */
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志工具类
        LogUtil.setLogEnable(true);
        instance = this;
        initARouter();
    }

    /**
     * 初始化ARouter路由框架
     */
    public void initARouter() {
        if (InitUtil.isApkInDebug(instance)) {
            //下面两行必须写在init之前，否则这些配置在init中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        //官方推荐放到Application中初始化
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //释放ARouter注入资源
        ARouter.getInstance().destroy();
    }
}


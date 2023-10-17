package zsdev.work.lib.support.mvp;

/**
 * Created: by 2023-09-20 11:04
 * Description: Activity基类其他业务接口
 * Author: 张松
 */
public interface IActivityInit {

    /**
     * 初始化网络监听，默认禁用
     *
     * @return true：开启网络监听 false：禁用网络监听
     */
    default boolean initNetworkStateListener() {
        return false;
    }

    /**
     * 初始化滑动返回
     *
     * @return true：开启左滑动 false：开启右滑动
     */
    default boolean initSwipeBackLayout() {
        return false;
    }

    /**
     * 初始化沉浸式状态栏
     *
     * @return true：开启沉浸 false：禁用沉浸
     */
    default boolean initImmersiveStatusBar() {
        return false;
    }

    /**
     * 初始化顶部隐藏状态栏
     *
     * @return true：开启隐藏 false：禁用隐藏
     */
    default boolean initTopHideStatus() {
        return false;
    }

    /**
     * 初始化底部导航栏状态栏
     *
     * @return true：开启隐藏 false：禁用隐藏
     */
    default boolean initBottomNaviCation() {
        return false;
    }

    /**
     * 初始化全屏显示
     *
     * @return true：状态栏+导航栏全透明 false：状态栏+导航栏非透明
     */
    default boolean initFullScreen() {
        return false;
    }

    /**
     * 初始化Activity流程开关状态，默认禁用
     *
     * @return true：开启业务方法 false：禁用业务方法
     */
    default boolean initSwitchActivityProcess() {
        return false;
    }
}

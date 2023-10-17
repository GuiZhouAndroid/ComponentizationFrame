package zsdev.work.lib.support.mvp;

/**
 * Created: by 2023-10-17 16:14
 * Description: Fragment基类其他业务接口
 * Author: 张松
 */
public interface IFragmentInit {
    /**
     * 初始化Fragment流程开关状态，默认禁用
     *
     * @return true：开启业务方法 false：禁用业务方法
     */
    default boolean initSwitchFragmentProcess() {
        return false;
    }
}

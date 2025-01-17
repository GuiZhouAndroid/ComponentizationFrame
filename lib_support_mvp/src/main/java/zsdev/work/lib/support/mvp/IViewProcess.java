package zsdev.work.lib.support.mvp;

/**
 * Created: by 2023-09-20 10:59
 * Description: BaseActivity、BaseMvpActivity/BaseFragment、BaseMvpFragment基类顶层通用业务接口
 * Author: 张松
 */
public interface IViewProcess {

    /**
     * 绑定视图View
     *
     * @return 布局文件的资源ID
     */
    default int viewBindLayout() {
        return 0;
    }

    /**
     * 初始化准备数据——>其它界面/各类初始数据
     */
    default void initData() {
    }

    /**
     * 设置监听事件
     */
    default void doClickListener() {
    }

    /**
     * View业务
     */
    default void doViewBusiness() {
    }
}

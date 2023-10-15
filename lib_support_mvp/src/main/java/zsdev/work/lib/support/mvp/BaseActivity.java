package zsdev.work.lib.support.mvp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;

import zsdev.work.lib.support.dialog.normal.DialogHelper;
import zsdev.work.lib.support.dialog.normal.OnDialogCancelListener;
import zsdev.work.lib.support.swipeback.SwipeBackActivity;
import zsdev.work.lib.support.swipeback.SwipeBackLayout;
import zsdev.work.lib.support.utils.ImmersiveUtil;
import zsdev.work.lib.support.utils.LogUtil;
import zsdev.work.lib.support.utils.ToastUtil;
import zsdev.work.lib.support.utils.network.newnet.NetworkLiveDataMBefore;
import zsdev.work.lib.support.utils.network.newnet.NetworkState;


/**
 * Created: by 2023-09-20 12:48
 * Description: 最基本的Activity，视图层V的Activity基类
 * Author: 张松
 */
public abstract class BaseActivity<VDB extends ViewDataBinding> extends SwipeBackActivity implements IActivityInitUI, IViewProcess, IActivity, View.OnClickListener, OnDialogCancelListener {
    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 继承DataBinding的子类
     */
    protected VDB topBaseActivityVDB;

    /**
     * 上下文
     **/
    public static Activity mNowActivity;

    /**
     * 标题栏
     */
    protected Toolbar mToolBar;

    /**
     * 是否初始化了toolbar
     */
    private boolean isInitToolbar = false;

    /**
     * 自定义对话框
     */
    protected DialogHelper mActivityDialogHelper;

    /**
     * 两次点击间隔不能少于500ms
     */
    private static final int FAST_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    /**
     * 设置网络状态监听
     *
     * @param isOpen true：开启网络监听 false：禁用网络监听
     */
    private void setNetworkStateListener(boolean isOpen) {
        if (isOpen) {
            // TODO: 2023/9/28  此处使用旧版网络监听，待使用新版网络监听，待修复其重复调用onCapabilitiesChanged()多次出现Toast提示
            NetworkLiveDataMBefore.getInstance(BaseApplication.getContext()).observe(this, this::getNetworkState);
        } else {
            LogUtil.i(TAG, "setNetworkStateListener: 禁用网络监听");
        }
    }

    /**
     * 设置滑动返回
     *
     * @param isOpen true：开启左滑动 false：开启右滑动
     */
    private void setSwipeBackLayout(boolean isOpen) {
        if (isOpen) {
            getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
            LogUtil.i(TAG, "setSwipeBackLayout: 左滑动返回");
        } else {
            getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
            LogUtil.i(TAG, "setSwipeBackLayout: 右滑动返回");
        }
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param isOpen true：开启沉浸 false：禁用沉浸
     */
    private void setImmersiveStatusBar(boolean isOpen) {
        //判断是否设置了沉浸式效果
        if (isOpen) {
            ImmersiveUtil.setStatusTransparent(this);
            LogUtil.i(TAG, "setImmersiveStatusBar: 顶部状态栏沉浸");
        } else {
            //ImmersiveUtil.setStatusTranslucent(this);
            LogUtil.i(TAG, "setImmersiveStatusBar: 顶部状态栏非沉浸");
        }
    }

    /**
     * 设置顶部隐藏状态栏
     *
     * @param isOpen：开启隐藏 false：禁用隐藏
     */
    private void setTopHideStatus(boolean isOpen) {
        if (isOpen) {
            ImmersiveUtil.setHideStatus(this);
            LogUtil.i(TAG, "setTopHideStatus: 顶部状态栏隐藏");
        } else {
            LogUtil.i(TAG, "setTopHideStatus: 顶部状态栏显示");
        }
    }

    /**
     * 设置底部隐藏导航栏，上滑激活显示
     *
     * @param isOpen true：开启隐藏 false：禁用隐藏
     */
    private void setBottomNaviCation(boolean isOpen) {
        if (isOpen) {
            ImmersiveUtil.setAutoFixHideNaviCation(this);
            LogUtil.i(TAG, "setBottomNaviCation: 底部导航栏隐藏");
        } else {
            LogUtil.i(TAG, "setBottomNaviCation: 底部导航栏显示");
        }
    }

    /**
     * 设置全屏显示：覆盖之前 状态栏+导航栏 的沉浸设置
     *
     * @param isOpen true：状态栏+导航栏全透明 false：状态栏+导航栏非透明
     */
    private void setFullScreen(boolean isOpen) {
        if (isOpen) {
            //效果等同 ：setBottomNaviCation(true) +  setImmersiveStatusBar(true)
            ImmersiveUtil.setStatusBottomAndNavigationTransparent(this);
            LogUtil.i(TAG, "setFullScreen: 全屏透明(状态栏+导航栏)");
        } else {
            //ImmersiveUtil.setStatusBottomAndNavigationTranslucent(this);
            LogUtil.i(TAG, "setFullScreen: 非全屏透明");
        }
    }

    /**
     * 1.创建视图View
     * 2.界面非正常销毁退出之前，自动将Activity状态现有数据以key-value形式存入onSaveInstanceState(@NonNull Bundle outState)中保存。
     * 3.初始创建调用OnCreate()方法时会读取上次已保存的key-value数据且赋值给变量savedInstanceState，提供给当前视图View使用。
     *
     * @param savedInstanceState Activity状态的key-value数据
     */
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate()");
        mNowActivity = this;//Activity上下文
        //创建自定义对话框
        if (mActivityDialogHelper == null) {
            mActivityDialogHelper = new DialogHelper(getActivity(), this);
        }
        //Activity的管理，将Activity压入栈
        BaseApplication.getActivityManagerUtil().pushActivity(this);
        setNetworkStateListener(initNetworkStateListener());//设置网络状态监听
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //强制竖屏
        setSwipeBackLayout(initSwipeBackLayout());//设置滑动返回
        setImmersiveStatusBar(initImmersiveStatusBar());//设置沉浸式状态栏
        setTopHideStatus(initTopHideStatus());//设置顶部状态栏隐藏
        setBottomNaviCation(initBottomNaviCation());//设置底部导航栏隐藏
        setFullScreen(initFullScreen());//设置全屏显示
        if (viewByResIdBindLayout() > 0) {
            //使用DataBindingUtil将布局与activity进行绑定
            topBaseActivityVDB = DataBindingUtil.setContentView(this, viewByResIdBindLayout());//将布局id关联DataBinding
        }
    }

    /**
     * 获取当前BaseActivity的ViewDataBinding实例
     *
     * @return ViewDataBinding实例
     */
    public VDB getTopBaseActivityVDB() {
        return topBaseActivityVDB;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isInitToolbar) {
            initToolbar();
        }
        LogUtil.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume()");
    }

    /**
     * 存储持久数据：DB、SP
     */
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i(TAG, "onStop()");
    }

    /**
     * Activity活动销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy()");
        BaseApplication.getActivityManagerUtil().popActivity(this);
        //释放对话框
        if (mActivityDialogHelper != null) {
            mActivityDialogHelper = null;
        }
        // TODO: 2023/10/15 最初是想在销毁当前Activity时清空持有引用。在其他活动同时持有此引用，会出现空指针异常
//        if (mNowActivity != null) {
//            mNowActivity = null;
//        }
    }

    /**
     * View单击的监听事件
     *
     * @param v 当前View对象
     */
    @Override
    public void onClick(View v) {
        viewClick(v);
    }

    /**
     * 初始化toolbar：渐变标题栏与沉浸适配
     * 如果子页面不需要初始化ToolBar，请直接覆写本方法做空操作即可
     */
    protected void initToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.base_toolbar);
        if (null != mToolBar) {
            // 设置为透明色
            mToolBar.setBackgroundColor(0x00000000);
            // 设置全透明
            mToolBar.getBackground().setAlpha(0);
            // 清除标题
            mToolBar.setTitle("");
            setSupportActionBar(mToolBar);
            // 子类中没有设置过返回按钮的情况下
            if (mToolBar.getNavigationIcon() == null) {
                //设置返回按钮
                mToolBar.setNavigationIcon(R.drawable.ic_chevron_left_write_24dp);
            }
            mToolBar.setNavigationOnClickListener(v -> finish());
            isInitToolbar = true;
            //返回文字按钮
            View navText = findViewById(R.id.toolbar_nav_text);
            if (null != navText) {
                navText.setOnClickListener(v -> finish());
            }
        }
        // appbar
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.base_appbar);
        // 状态栏高度 getStatusBarHeight只是一个获取高度的方法
        int statusBarHeight = ImmersiveUtil.getStatusBarHeight(BaseApplication.getContext());
        //大于 19  设置沉浸和padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mAppBarLayout != null) {
                ViewGroup.MarginLayoutParams appbarLayoutParam = (ViewGroup.MarginLayoutParams) mAppBarLayout.getLayoutParams();
                // 更改高度 toolbar_height 的高度是可配置的
                appbarLayoutParam.height = (int) (getResources().getDimension(R.dimen.toolbar_height) + statusBarHeight);
                // 设置padding
                mAppBarLayout.setPadding(mAppBarLayout.getPaddingLeft(), statusBarHeight, mAppBarLayout.getPaddingRight(), mAppBarLayout.getPaddingBottom());
                //重新设置回去
                mAppBarLayout.setLayoutParams(appbarLayoutParam);
            }
        }
    }

    /**
     * 启动Fragment
     *
     * @param id       id
     * @param fragment 碎片
     */
    protected void startFragment(int id, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    /**
     * 得到Activity
     *
     * @return 上下文
     */
    public static Activity getActivity() {
        return mNowActivity;
    }

    @Override
    public void onDialogCancelListener(AlertDialog dialog) {
        if (mActivityDialogHelper != null) {
            mActivityDialogHelper.dismissDialog();
        }
    }

    /**
     * 获取状态并在界面显示、
     *
     * @param networkState 网络状态
     */
    private void getNetworkState(NetworkState networkState) {
        switch (networkState) {
            case NOT_NETWORK_CHECK:
                Toast.makeText(getActivity(), NetworkState.NOT_NETWORK_CHECK.getDesc(), Toast.LENGTH_SHORT).show();
                break;
            case NETWORK_CONNECT_Fail:
                Toast.makeText(getActivity(), NetworkState.NETWORK_CONNECT_Fail.getDesc(), Toast.LENGTH_SHORT).show();
                break;
            case WIFI:
                Toast.makeText(getActivity(), NetworkState.WIFI.getDesc(), Toast.LENGTH_SHORT).show();
                break;
            case MOBILE:
                Toast.makeText(getActivity(), NetworkState.MOBILE.getDesc(), Toast.LENGTH_SHORT).show();
                break;
            case ETHERNET:
                Toast.makeText(getActivity(), NetworkState.ETHERNET.getDesc(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 短显示：Toast消息提示
     *
     * @param charSequence 字符文本
     */
    protected void shortShowMsg(CharSequence charSequence) {
        ToastUtil.showShort(getApplication(), charSequence);
    }

    /**
     * 短显示：Toast消息提示  字符
     *
     * @param msg 字符文本
     */
    protected void shortShowMsg(String msg) {
        ToastUtil.showShort(getApplication(), msg);
    }

    /**
     * 短显示：Toast消息提示
     *
     * @param resourceId 资源ID
     */
    protected void shortShowMsg(int resourceId) {
        ToastUtil.showShort(getApplication(), resourceId);
    }

    /**
     * 长显示：Toast消息提示
     *
     * @param charSequence 字符文本
     */
    protected void longShowMsg(CharSequence charSequence) {
        ToastUtil.showLong(getApplication(), charSequence);
    }

    /**
     * 长显示：Toast消息提示  字符
     *
     * @param msg 字符文本
     */
    protected void longShowMsg(String msg) {
        ToastUtil.showLong(getActivity(), msg);
    }

    /**
     * 长显示：Toast消息提示
     *
     * @param resourceId 资源ID
     */
    protected void longShowMsg(int resourceId) {
        ToastUtil.showLong(getApplication(), resourceId);
    }

    /**
     * 两次点击间隔不能少于500ms
     *
     * @return flag
     */
    protected static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    /**
     * 结束当前Activity，页面返回
     */
    protected void backFinish() {
        //首次点击时
        getActivity().finish();
        //非快速点击时
        if (!isFastClick()) {
            getActivity().finish();
        }
    }

    /**
     * 结束当前Activity，Toolbar返回
     *
     * @param toolbar toolbar
     */
    protected void backFinish(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().finish();
            if (!isFastClick()) {
                getActivity().finish();
            }
        });
    }
}


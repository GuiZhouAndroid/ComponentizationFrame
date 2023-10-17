package zsdev.work.lib.support.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import zsdev.work.lib.support.dialog.normal.DialogHelper;
import zsdev.work.lib.support.dialog.normal.OnDialogCancelListener;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-09-20 12:49
 * Description: 最基本的Fragment，视图层V的Fragment基类。
 * Author: 张松
 */
public abstract class BaseFragment<VDB extends ViewDataBinding> extends Fragment implements IFragmentInit, IViewProcess, IFragment, OnDialogCancelListener {

    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 继承DataBinding的子类
     */
    protected VDB topBaseFragmentVDB;

    /**
     * 当前Fragment所绑定的Activity实例
     */
    protected Activity mNowFragmentOfActivity;

    /**
     * 自定义对话框
     */
    protected DialogHelper mFragmentDialogHelper;

    /**
     * 上下文
     **/
    protected Activity mNowActivity;

    /**
     * 绑定activity
     * Fragment和Activity相关联时调用。可以通过该方法获取Activity引用，还可以通过getArguments()获取参数
     *
     * @param context 上下文
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogUtil.i(TAG, "onAttach()");
    }

    /**
     * 运行在onAttach之后，可以接收别人传递过来的参数
     * Fragment被创建时调用
     *
     * @param savedInstanceState Bundle数据
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate()");
        mNowFragmentOfActivity = getActivity();
    }

    /**
     * 运行在onCreate之后，生成View视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG, "onCreateView()");
        //使用父类的布局View
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 在onCreateView（LayoutInflater、ViewGroup、Bundle）返回之后立即调用，
     * 但在将任何保存的状态还原到视图之前调用。这给了子类一个机会，一旦它们知道自己
     * 的视图层次结构已经完全创建，就可以对自己进行初始化。然而，片段的视图层次结构此时并未附加到其父级。
     *
     * @param view               当前View对象
     * @param savedInstanceState Bundle对象
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "onViewCreated()");
        //创建自定义对话框
        if (mFragmentDialogHelper == null) {
            mFragmentDialogHelper = new DialogHelper(getFragmentOfActivity(), this);
        }
        //使用DataBindingUtil将布局与Fragment进行绑定
        topBaseFragmentVDB = DataBindingUtil.bind(view);
        //使用BaseFragment必须设置true
        if (initSwitchFragmentProcess()) {
            initData(); //初始化准备数据
            doClickListener(); //监听事件
            doViewBusiness(); //View业务
        }
    }

    /**
     * 获取当前Fragment的ViewDataBinding实例
     *
     * @return ViewDataBinding实例
     */
    public VDB getTopBaseFragmentVDB() {
        return topBaseFragmentVDB;
    }

    /**
     * 当Activity完成onCreate()时调用
     *
     * @param savedInstanceState Bundle数据
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(TAG, "onActivityCreated()");
    }

    /**
     * 当Fragment可见时调用
     */
    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(TAG, "onStart()");
    }

    /**
     * 当Fragment可见且可交互时调用
     */
    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume()");
    }

    /**
     * 当Fragment不可交互但可见时调用
     */
    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(TAG, "onPause()");
    }

    /**
     * 当Fragment不可见时调用
     */
    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i(TAG, "onStop()");
    }

    /**
     * 当Fragment的UI从视图结构中移除时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(TAG, "onDestroyView()");
        //释放对话框
        if (mFragmentDialogHelper != null) {
            mFragmentDialogHelper = null;
        }
    }


    /**
     * Fragment绑定Activity时调用
     *
     * @param activity activity
     */
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        LogUtil.i(TAG, "onAttach()");
        this.mNowActivity = (Activity) activity;
    }

    /**
     * 销毁Fragment时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy()");
        // TODO: 2023/10/14 有可能导致空指针异常，使用Fragment时需测试
        //释放对话框
        if (mFragmentDialogHelper != null) {
            mFragmentDialogHelper = null;
        }
    }

    /**
     * 当Fragment和Activity解除关联时调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
        this.mNowActivity = null;
        LogUtil.i(TAG, "onDetach()");
        //释放对话框
        if (mFragmentDialogHelper != null) {
            mFragmentDialogHelper = null;
        }
    }

    /**
     * 跳转Fragment
     *
     * @param toFragment 跳转到的fragment
     * @param tag        fragment的标签
     */
    public void startFragment(Fragment toFragment, String tag) {
        LogUtil.i(TAG, "startFragment()");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(this).add(android.R.id.content, toFragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 跳转Fragment
     *
     * @param toFragment 跳转去的fragment
     */
    public void startFragment(Fragment toFragment) {
        LogUtil.i(TAG, "startFragment()");
        LogUtil.i(TAG, this + "——>已成功跳转——>" + toFragment.getClass());
        startFragment(toFragment, null);
    }

    /**
     * fragment进行回退
     * 类似于activity的OnBackPress
     */
    public void onFragmentBack() {
        LogUtil.i(TAG, "onFragmentBack()");
        getFragmentManager().popBackStack();
    }

    /**
     * 得到Context
     *
     * @return 上下文
     */
    public Context getNowFragmentContext() {
        return getContext();
    }

    /**
     * 得到fragment
     *
     * @return fragment
     */
    public Fragment getNowFragment() {
        return this;
    }

    /**
     * 得到当前Fragment所绑定的Activity实例
     *
     * @return Activity
     */
    public Activity getFragmentOfActivity() {
        return mNowFragmentOfActivity;
    }
}


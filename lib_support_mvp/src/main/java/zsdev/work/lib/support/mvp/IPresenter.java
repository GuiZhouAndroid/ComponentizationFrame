package zsdev.work.lib.support.mvp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import zsdev.work.lib.support.utils.LogUtil;

/**
 * Created: by 2023-09-20 11:03
 * Description: P层顶层业务接口
 * Author: 张松
 */
public interface IPresenter extends DefaultLifecycleObserver {

    void setLifecycleOwner(LifecycleOwner lifecycleOwner);

    @Override
    default void onCreate(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onCreate: ");
    }

    @Override
    default void onStart(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onStart: ");
    }

    @Override
    default void onResume(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onResume: ");
    }

    @Override
    default void onPause(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onPause: ");
    }

    @Override
    default void onStop(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onStop: ");
    }

    @Override
    default void onDestroy(@NonNull LifecycleOwner owner) {
        LogUtil.i("IPresenter", "onDestroy: ");
    }
}

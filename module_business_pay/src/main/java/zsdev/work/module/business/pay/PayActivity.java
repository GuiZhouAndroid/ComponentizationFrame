package zsdev.work.module.business.pay;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;

import zsdev.work.lib.support.mvp.base.BaseActivity;

/**
 * Created: by 2023-10-07 21:44
 * Description:
 * Author: 张松
 */
@Route(path = "/pay/all")
public class PayActivity extends BaseActivity {
    @Override
    public int viewByResIdBindLayout() {
        return R.layout.pay_activity;
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stringExtra = getIntent().getStringExtra("组件开发【支付】");
        Log.i(TAG, "onCreate: " + stringExtra);
    }
}

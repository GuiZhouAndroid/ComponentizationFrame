package zsdev.work.module.business.pay;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import zsdev.work.lib.frame.core.arouter.AliRouterPathManager;
import zsdev.work.lib.support.mvp.BaseActivity;
import zsdev.work.lib.support.utils.LogUtil;

/**
 * Created: by 2023-10-07 21:44
 * Description:
 * Author: 张松
 */
@Route(path = AliRouterPathManager.MODULE_BUSINESS_PAY_ACTIVITY)
public class PayActivity extends BaseActivity {

    @Autowired
    public String key1;

    @Override
    public int viewByResIdBindLayout() {
        return R.layout.pay_activity;
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String stringExtra = getIntent().getStringExtra("组件开发【支付】");
        LogUtil.i(TAG, "onCreate: " + stringExtra);
        //注入ARouter
        ARouter.getInstance().inject(this);
        if (key1 != null) {
            shortShowMsg(key1);
        } else {
            longShowMsg("当前是开发模式！");
        }
    }
}

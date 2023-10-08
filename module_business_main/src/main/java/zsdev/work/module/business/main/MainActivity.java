package zsdev.work.module.business.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.launcher.ARouter;

import zsdev.work.lib.frame.core.arouter.AliRouterPathManager;
import zsdev.work.lib.frame.core.bean.User;
import zsdev.work.lib.support.mvp.BaseActivity;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-10-07 21:49
 * Description:
 * Author: 张松
 */
public class MainActivity extends BaseActivity {

    @Override
    public int viewByResIdBindLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String stringExtra = getIntent().getStringExtra("组件开发【主页】");
        LogUtil.i(TAG, "onCreate: " + stringExtra);

        Button btnOneself = findViewById(R.id.btn_oneself);
        Button btnPay = findViewById(R.id.btn_pay);
        btnOneself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(AliRouterPathManager.MODULE_BUSINESS_ONESELF_ACTIVITY)
                        .withFloat("myInfo1", 25.3F)
                        .withString("myInfo2", "1998年2月1日")
                        .withSerializable("ser", new User("张松", "男"))
                        .navigation();
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                //Uri testUriMix = Uri.parse("arouter://m.aliyun.com/pay/all");
                //ARouter.getInstance().build(testUriMix).withOptionsCompat(compat).withString("key1", "value1").withOptionsCompat(compat).navigation();
                ARouter.getInstance().build(AliRouterPathManager.MODULE_BUSINESS_PAY_ACTIVITY)
                        .withOptionsCompat(compat)
                        .withString("key1", "我是传给PayActivity的路由值")
                        .navigation();

            }
        });
    }
}


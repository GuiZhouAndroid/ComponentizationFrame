package zsdev.work.module.support.test.app;

import android.view.View;
import android.widget.Toast;

import zsdev.work.lib.support.mvp.BaseMvpActivity;
import zsdev.work.lib.support.utils.LogUtil;
import zsdev.work.module.support.test.app.bean.My;
import zsdev.work.module.support.test.app.contract.MainContract;
import zsdev.work.module.support.test.app.databinding.ActivityMainBinding;
import zsdev.work.module.support.test.app.presenter.MainPresenter;


public class MainActivity extends BaseMvpActivity<MainPresenter, ActivityMainBinding> implements MainContract.View {

    @Override
    public boolean initNetworkStateListener() {
        return true;
    }

    @Override
    public void onSuccessNetUI(String successData) {
        showSuccessDialog(successData);
    }

    @Override
    public int viewByResIdBindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean initSwipeBackLayout() {
        return true;
    }

    @Override
    public boolean initImmersiveStatusBar() {
        return true;
    }

    @Override
    public void loginSuccess(My responseObject) {
        showSuccessDialog(responseObject.getName());
    }

    @Override
    public void loginFailure(String message) {
        LogUtil.i(TAG, "loginFailure: " + message);
        showErrorDialog(message);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void doViewBusiness() {
        initView();
    }

    public void initView() {
        vb.btnSigninLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUsername().isEmpty() || getPassword().isEmpty()) {
                    Toast.makeText(MainActivity.this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                getPresenter().login2();
            }
        });

    }

    /**
     * @return 帐号
     */
    private String getUsername() {
        return vb.etUsernameLogin.getText().toString().trim();
    }

    /**
     * @return 密码
     */
    private String getPassword() {
        return vb.etPasswordLogin.getText().toString().trim();
    }
}
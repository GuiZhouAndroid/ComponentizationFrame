package develop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import zsdev.work.module.business.oneself.OneSelfActivity;

/**
 * Created: by 2023-10-07 21:46
 * Description:
 * Author: 张松
 */
@SuppressLint("CustomSplashScreen")
public class OneSelfLaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里传值给需要调试的Activity
        Intent intent = new Intent(this, OneSelfActivity.class);
        intent.putExtra("组件开发【我的】", "【我的】我是组件开发模式模拟传值");
        startActivity(intent);
        finish();
    }
}

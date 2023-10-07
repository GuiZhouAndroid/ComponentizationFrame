package develop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import zsdev.work.module.business.main.MainActivity;

/**
 * Created: by 2023-10-07 21:48
 * Description:
 * Author: 张松
 */
@SuppressLint("CustomSplashScreen")
public class MainLaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里传值给需要调试的Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("组件开发【主页】", "【主页】我是组件开发模式模拟传值");
        startActivity(intent);
        finish();
    }
}

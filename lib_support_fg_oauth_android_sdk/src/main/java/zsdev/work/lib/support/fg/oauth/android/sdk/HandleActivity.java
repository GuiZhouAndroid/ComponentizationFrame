package zsdev.work.lib.support.fg.oauth.android.sdk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

/**
 * Created: by 2023-10-19 17:05
 * Description:
 * Author: 张松
 */
public class HandleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mAppId = getIntent().getStringExtra("mAppId");
        String mSecret = getIntent().getStringExtra("mSecret");
        Log.i("HandleActivity==AppId=", Objects.requireNonNull(mAppId));
        Log.i("HandleActivity==Secret=", Objects.requireNonNull(mSecret));

        Intent intent = new Intent();
        //启动应用的包名称，启动的Activity或者Service的全称（包名+类名）
        intent.setComponent(new ComponentName("com.zhangsong.fgstore", "com.zhangsong.fgstore.OauthActivity"));
        //待传参数
        intent.putExtra("HandleAppId", mAppId);
        intent.putExtra("HandleSecret", mSecret);
        startActivityForResult(intent, 25);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 25 && resultCode == 26) {
            String code = data.getStringExtra("code");
            if (code != null) {
                Log.i("onActivityResult=code=", code);
                Intent intent = new Intent();
                intent.putExtra("CallBackCode", code);
                // 参数是动态的flag（标志）
                intent.setAction(FGApiImpl.OAUTH_CODE);
                //发送广播
                sendBroadcast(intent);
                HandleActivity.this.finish();
            }
        }
    }
}

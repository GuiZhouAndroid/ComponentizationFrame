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
    private static final int REQUEST_CODE_OAUTH = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传递的 appId 和 secret
        String mAppId = getIntent().getStringExtra("mAppId");
        String mSecret = getIntent().getStringExtra("mSecret");
        Log.i("HandleActivity==AppId=", Objects.requireNonNull(mAppId));
        Log.i("HandleActivity==Secret=", Objects.requireNonNull(mSecret));

        Intent intent = new Intent();
        //启动OauthActivity
        //启动应用的包名称，启动的Activity或者Service的全称（包名+类名）
        intent.setComponent(new ComponentName("com.zhangsong.fgstore", "com.zhangsong.fgstore.OauthActivity"));
        //待传参数
        intent.putExtra("HandleAppId", mAppId);
        intent.putExtra("HandleSecret", mSecret);
        startActivityForResult(intent, REQUEST_CODE_OAUTH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IOAuthResponse oauthResponse = FGAPIFactory.mIOAuthResponse;
        if (null == oauthResponse) {
            throw new RuntimeException("Please first implement the OAuthCallback interface in the view and register FGAPIFactory.setOAuthOnCallbackListener(this) to pass in the instance!");
        }
        if (data != null) {
            // 同意授权返回
            if (requestCode == REQUEST_CODE_OAUTH && resultCode == RESULT_OK) {
                String strCode = data.getStringExtra("code");
                String strRspCode = data.getStringExtra("RspCode");
                String strRspMsg = data.getStringExtra("RspMsg");
                if (null != strCode && null != strRspCode && null != strRspMsg) {
                    Log.i("HandleActivity", "onActivityResult 同意授权 授权code=" + strCode + "，状态code=" + strRspCode + "，msg=" + strRspMsg);
                    // 1.广播方式回调code
                    //Intent intent = new Intent();
                    //intent.putExtra("CallBackCode", code);
                    //intent.setAction(FGApiImpl.OAUTH_CODE); //参数是动态的flag（标志）
                    //sendBroadcast(intent);//发送广播
                    // 2.接口方式回调获取到code值后，通过callBackOauthResponse回调给应用方
                    oauthResponse.callBackOauthResponse(new OauthRep(Integer.parseInt(strRspCode), strRspMsg, strCode));
                }
            }
            // 拒接授权返回
            if (requestCode == REQUEST_CODE_OAUTH && resultCode == RESULT_CANCELED) {
                String strRspCode = data.getStringExtra("RspCode");
                String strRspMsg = data.getStringExtra("RspMsg");
                if (null != strRspCode && null != strRspMsg) {
                    Log.i("HandleActivity", "onActivityResult 拒接授权 无授权code，状态code=" + strRspCode + "，msg=" + strRspMsg);
                    oauthResponse.callBackOauthResponse(new OauthRep(Integer.parseInt(strRspCode), strRspMsg, null));
                }
            }
            // TODO: 2023/10/25 待修复 取消授权
            // 取消授权返回
//            if (requestCode == REQUEST_CODE_OAUTH && resultCode == RESULT_FIRST_USER) {
//                String strRspCode = data.getStringExtra("RspCode");
//                String strRspMsg = data.getStringExtra("RspMsg");
//                if (null != strRspCode && null != strRspMsg) {
//                    Log.i("HandleActivity", "onActivityResult 取消授权 无授权code，状态code=" + strRspCode + "，msg=" + strRspMsg);
//                    oauthResponse.callBackOauthResponse(new OauthRep(Integer.parseInt(strRspCode), strRspMsg, null));
//                }
//            }
        } else {
            Log.i("HandleActivity", "onActivityResult无回调数据");
            oauthResponse.callBackOauthResponse(new OauthRep(20003, "用户取消授权", null));
        }
        HandleActivity.this.finish();
    }
}

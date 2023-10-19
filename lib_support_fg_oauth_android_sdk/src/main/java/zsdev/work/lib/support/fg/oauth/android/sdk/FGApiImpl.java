package zsdev.work.lib.support.fg.oauth.android.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

/**
 * Created: by 2023-10-18 14:45
 * Description:
 * Author: 张松
 */
public class FGApiImpl implements IFGApi {
    public static final String OAUTH_CODE = "zsdev.work.lib.support.fg.oauth.android.sdk.HandleActivity";

    public Context mContext;
    protected String mAppId;
    protected String mSecret;

    public FGApiImpl(Context context) {
        this.mContext = context;
        init();
    }

    /**
     * 初始化获取应用id和密钥
     */
    public void init() {
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        Bundle metaData = applicationInfo.metaData;
        if (metaData != null) {
            int strAppId = metaData.getInt("com.zhangsong.fgstore.appid");
            String strSecret = metaData.getString("com.zhangsong.fgstore.secret");
            if (0 == strAppId || null == strSecret) {
                throw new RuntimeException("You must register all FuGui's appId or secrets in the AndroidManifest list!");
            }
            mAppId = String.valueOf(strAppId);
            mSecret = strSecret;
            Log.i("FGAPIFactory==AppId=", String.valueOf(Objects.requireNonNull(strAppId)));
            Log.i("FGAPIFactory==Secret=", Objects.requireNonNull(strSecret));
        } else {
            throw new RuntimeException("You must register all FuGui's appId or secrets in the AndroidManifest list!");
        }
    }

    @Override
    public boolean registerApp(String appId) {
        Log.i("registerApp", appId);
        return false;
    }

    /**
     * 检测安装
     *
     * @return 返回安装结果
     */
    @Override
    public boolean isFgAppInstalled() {
        return isApplicationAvailable2();
    }

    @Override
    public boolean sendReq() {
        return false;
    }

    @Override
    public String getAppId() {
        if (null == mAppId)
            throw new RuntimeException("Please first call FGAPIFactory.init (Context)");
        return mAppId;
    }

    @Override
    public String getAppSecret() {
        if (null == mSecret)
            throw new RuntimeException("Please first call FGAPIFactory.init (Context)");
        return mSecret;
    }

    /**
     * 必须使用addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)来确定使用标志。否则会出以下错误
     * Calling startActivity() from outside of an Activity  context requires the
     * FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
     *
     * @return
     */
    @Override
    public boolean handleIntent() {
        Intent intent = new Intent(mContext, HandleActivity.class);
        intent.putExtra("mAppId", mAppId);
        intent.putExtra("mSecret", mSecret);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //向意图添加其他标志（或使用现有标志值）
        mContext.startActivity(intent);
        return false;
    }

    @Override
    public boolean openFGApp() {
//        Intent intent = new Intent();
//        //启动应用的包名称，启动的Activity或者Service的全称（包名+类名）
//        intent.setComponent(new ComponentName("com.zhangsong.fgstore", "com.zhangsong.fgstore.OauthActivity"));
//        //待传参数
//        intent.putExtra("appId", "asdadsa");
//        startActivity(intent);
        return false;
    }

    /**
     * 判断秀水胡贵APP是否安装
     *
     * @return true 已安装 false未安装
     */
    public boolean isApplicationAvailable2() {
        try {
            return mContext.getPackageManager().getPackageInfo("com.zhangsong.fgstore", 25) != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}


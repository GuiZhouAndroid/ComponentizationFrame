package zsdev.work.lib.support.fg.oauth.android.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created: by 2023-10-18 14:45
 * Description:
 * Author: 张松
 */
public class FGApiImpl implements IFGApi {
    public Context mContext;

    //授权请求实体
    protected OauthReq oauthReq;

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
            oauthReq = new OauthReq(String.valueOf(strAppId), strSecret);
            Log.i("FGAPIFactory==AppId=", oauthReq.getAppId());
            Log.i("FGAPIFactory==Secret=", oauthReq.getAppSecret());
        } else {
            throw new RuntimeException("You must register all FuGui's appId or secrets in the AndroidManifest list!");
        }
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

    /**
     * 获取应用id
     *
     * @return appId
     */
    @Override
    public String getAppId() {
        if (null == oauthReq.getAppId())
            throw new RuntimeException("Please first call FGAPIFactory.createFGApi (Context)");
        return oauthReq.getAppId();
    }

    /**
     * 获取应用密钥
     *
     * @return secret
     */
    @Override
    public String getAppSecret() {
        if (null == oauthReq.getAppSecret())
            throw new RuntimeException("Please first call FGAPIFactory.createFGApi (Context)");
        return oauthReq.getAppSecret();
    }

    /**
     * 请求处理授权认证
     * 必须使用addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)来确定使用标志。否则会出以下错误
     * Calling startActivity() from outside of an Activity  context requires the
     * FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
     */
    @Override
    public void handleOAuthRequest() {
        Intent intent = new Intent(mContext, HandleActivity.class);
        intent.putExtra("mAppId", getAppId());
        intent.putExtra("mSecret", getAppSecret());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //向意图添加其他标志（或使用现有标志值）
        mContext.startActivity(intent);
    }

    /**
     * 是否安装胡贵APP
     *
     * @return true-已安装 false-未安装
     */
    public boolean isApplicationAvailable2() {
        try {
            return mContext.getPackageManager().getPackageInfo("com.zhangsong.fgstore", 25) != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}


package zsdev.work.lib.frame.core.bean;

/**
 * Created: by 2023-10-15 01:31
 * Description:
 * Author: 张松
 */
public class C {
    //管理中心-移动应用-查看对应的申请的AppID
    public static final String APP_ID = "wx1a3086eaa7ecf8c5";
    public static final String SECRET = "92128ce4d9493d3dfa50b586e313f1db";

    public static final String REDIRECT_URL = "http://154.8.192.204:9002/?code=";
    public static final String GET_AUTHORIZE_CODE_URL = "http://154.8.192.204:9001/oauth2/authorize?response_type=code";

    //public static final String GET_AUTHORIZE_CODE_URL = "http://154.8.192.204:9001/oauth2/authorize?response_type=code";




    //1.向服务器9001获取授权码（用户登录才可以授权）：http://154.8.192.204:9001/oauth2/authorize?response_type=code&client_id=1001&redirect_uri=http://154.8.192.204:9002/&scope=userinfo

    //2.获得授权码后携带code重定向到客户端9002：http://154.8.192.204:9002/?code=DoYYJnbhn7WjzSUnuxOmDJsoyFzDOKW1xkM2VLailWd0jY54qovUznLJyHb8

    //3.向服务器9001获取token：http://154.8.192.204:9001/oauth2/token?grant_type=authorization_code&client_id=1001&client_secret=aaaa-bbbb-cccc-dddd-eeee&code=okqBYVXYW94ZUY9mE1zgo2Ta2Va4tvAEt4ERXgemCj8OyhLHNCqguIxHT5mR
}

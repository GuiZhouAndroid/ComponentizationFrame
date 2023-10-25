package zsdev.work.lib.support.fg.oauth.android.sdk;

/**
 * Created: by 2023-10-25 01:04
 * Description: 授权回调实体
 * Author: 张松
 */
public class OauthRep {
    /**
     * 状态Code
     */
    private int RspCode;
    /**
     * 状态message
     */
    private String RspMsg;
    /**
     * 授权Code
     */
    private String code;

    public OauthRep(int rspCode, String rspMsg, String code) {
        RspCode = rspCode;
        RspMsg = rspMsg;
        this.code = code;
    }

    public int getRspCode() {
        return RspCode;
    }

    public void setRspCode(int rspCode) {
        RspCode = rspCode;
    }

    public String getRspMsg() {
        return RspMsg;
    }

    public void setRspMsg(String rspMsg) {
        RspMsg = rspMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "OauthRep{" +
                "RspCode=" + RspCode +
                ", RspMsg='" + RspMsg + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

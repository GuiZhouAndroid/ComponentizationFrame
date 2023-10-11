package zsdev.work.lib.support.network.exception;

/**
 * Created: by 2023-09-09 01:25
 * Description: 自定义服务器异常，是前端和后端约束的cde和msg，而不是http类型的服务器内部错误
 * Author: 张松
 */
public class ServerException extends RuntimeException {

    /**
     * 异常状态码
     */
    public int code;

    /**
     * 异常原因
     */
    public String msg;

    public ServerException() {

    }
    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ServerException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}

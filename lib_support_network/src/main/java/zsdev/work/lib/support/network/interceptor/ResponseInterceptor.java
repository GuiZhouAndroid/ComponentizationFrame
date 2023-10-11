package zsdev.work.lib.support.network.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zsdev.work.lib.support.utils.LogUtil;

/**
 * Created: by 2023-10-11 16:42
 * Description: 网络耗时拦截器，主要用户监听网络请求耗时时间，以发挥优化网络的作用
 * Author: 张松
 */
public class ResponseInterceptor implements InterceptorHandler {

    private long requestTime;

    /**
     * 请求之前的拦截请求数据：请求开始
     *
     * @param request 请求对象
     * @param chain   chain
     * @return 请求数据
     */
    @Override
    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
        //获取开始之前时间戳
        requestTime = System.currentTimeMillis();
        LogUtil.i("InterceptorImpl", "onBeforeRequest()：Request start..." + requestTime);
        return request;
    }

    /**
     * 请求之后：响应结束
     *
     * @param response 响应对象
     * @param chain    chain
     * @return 响应数据
     */
    @Override
    public Response onAfterRequest(Response response, Interceptor.Chain chain) {
        LogUtil.i("InterceptorImpl", "onAfterRequest()：Response end..." + "SpendTotalTime=" + (System.currentTimeMillis() - requestTime) + "ms");
        return response;
    }
}

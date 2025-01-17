package zsdev.work.module.support.test.app;


import zsdev.work.lib.support.network.NetworkHelper;
import zsdev.work.module.support.test.app.service.APIService;

/**
 * Created: by 2023-09-26 18:59
 * Description:
 * Author: 张松
 */
public class ServiceBuildHelper {

    public static final String API_BASE_URL = "https://www.zsdev.work/";
    /**
     * 用户服务
     */
    private static APIService apiService;

    /**
     * 通过RetrofitHelper创建返回Service
     *
     * @return
     */
    public static synchronized APIService getApiService() {
        if (apiService == null) {
            synchronized (ServiceBuildHelper.class) {
                if (apiService == null) {
                    apiService = NetworkHelper.getInstance().getApiServiceClass(API_BASE_URL, APIService.class, new NetWorkConfig());
                }
            }
        }
        return apiService;
    }
}

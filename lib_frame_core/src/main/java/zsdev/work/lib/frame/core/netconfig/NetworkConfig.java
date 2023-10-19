package zsdev.work.lib.frame.core.netconfig;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import retrofit2.Converter;
import zsdev.work.lib.support.network.INetworkConfig;
import zsdev.work.lib.support.network.enums.ConverterMode;
import zsdev.work.lib.support.network.enums.CookieStoreMode;
import zsdev.work.lib.support.network.interceptor.InterceptorHandler;

/**
 * Created: by 2023-10-15 22:47
 * Description:
 * Author: 张松
 */
public class NetworkConfig implements INetworkConfig {

    private final Application application;

    public NetworkConfig(Application application) {
        this.application = application;
    }

    @Override
    public Application setApplicationContext() {
        return application;
    }

    @Override
    public Interceptor[] setInterceptors() {
        return new Interceptor[0];
    }

    @Override
    public long setConnectTimeoutMills() {
        return 0;
    }

    @Override
    public long setReadTimeoutMills() {
        return 0;
    }

    @Override
    public long setWriteTimeoutMills() {
        return 0;
    }

    @Override
    public boolean setIsEnableOkpDefaultPrintLog() {
        return true;
    }

    @Override
    public boolean setIsEnableCache() {
        return false;
    }

    @Override
    public boolean setEnableCookieStore() {
        return false;
    }

    @Override
    public int setCacheMaxAgeTimeUnitSeconds() {
        return 0;
    }

    @Override
    public int setCacheMaxStaleTimeUnitSeconds() {
        return 0;
    }

    @Override
    public boolean setIsEnableRetryOnConnection() {
        return false;
    }

    @Override
    public Map<String, String> setUrlParameter() {
        return null;
    }

    @Override
    public Map<String, String> setHeaderParameters() {
        Map<String, String> map = new HashMap<>();
        map.put("OS_TYPE", "Android");
        map.put("APP_VERSION", "1.0");
        return map;
    }

    @Override
    public CookieJar setCustomCookieStore() {
        return null;
    }

    @Override
    public InterceptorHandler[] setCustomInterceptor() {
        return new InterceptorHandler[0];
    }

    @Override
    public ConverterMode setConverterFactoryMode() {
        return ConverterMode.MOSHI;
    }

    @Override
    public CookieStoreMode setCookieStoreMode() {
        return CookieStoreMode.SP;
    }

    @Override
    public Converter.Factory setCustomConverterFactory() {
        return null;
    }
}

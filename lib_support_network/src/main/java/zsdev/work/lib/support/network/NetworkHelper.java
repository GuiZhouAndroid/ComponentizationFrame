package zsdev.work.lib.support.network;

import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import zsdev.work.lib.support.network.cookie.CookieJarImpl;
import zsdev.work.lib.support.network.cookie.DBCookieStore;
import zsdev.work.lib.support.network.cookie.MemoryCookieStore;
import zsdev.work.lib.support.network.cookie.SpCookieStore;
import zsdev.work.lib.support.network.interceptor.CachesInterceptor;
import zsdev.work.lib.support.network.interceptor.HeadersInterceptor;
import zsdev.work.lib.support.network.interceptor.InterceptorHandler;
import zsdev.work.lib.support.network.interceptor.InterceptorImpl;
import zsdev.work.lib.support.network.interceptor.ResponseInterceptor;
import zsdev.work.lib.support.network.interceptor.UrlParameterInterceptor;
import zsdev.work.lib.support.utils.LogUtil;


/**
 * Created: by 2023-09-06 00:11
 * Description: 网络配置帮助类（retrofit2 + okhttp3 + rxjava2 + Gson 等封装帮助类） 全局配置1个，单次配置N个
 * 使用rxjava转换器处理线程调度的帮助类.每一个Presenter中使用到IO子线程与Main主线程切换，每次都需要重新写就比较繁琐，而RxJava中根据APIService返回值Observable或者Flowable类型
 * 自动从匹配相应抽象类中通过compose()完成线程合并自动切换。
 * 返回值类型为Observable<T>时：Observable.compose("使用RxSchedulerHelper类型返回值对应的getObservableScheduler()作为参数")
 * 返回值类型为Flowable<T>时：Flowable.compose("使用RxSchedulerHelper类型返回值对应的getFlowableScheduler()作为参数")
 * Scheduler是RxJava的线程调度器，可以指定代码执行的线程。RxJava内置了几种线程：
 * AndroidSchedulers.mainThread() 主线程
 * Schedulers.immediate() 当前线程，即默认Scheduler
 * Schedulers.newThread() 启用新线程
 * Schedulers.io() IO线程，内部是一个数量无上限的线程池，可以进行文件、数据库和网络操作。
 * Schedulers.computation() CPU计算用的线程，内部是一个数目固定为CPU核数的线程池，适合于CPU密集型计算，不能操作文件、数据库和网络。
 * 一般的网络请求应该使用io,因为io使用了无限的线程池，而newThread没有线程池维护
 * onNext里我们的函数发生异常时，onError会被调用
 * subscribeOn(Schedulers.io()) 指定了 Observable 在 IO 线程中创建和发射数据，而 observeOn(Schedulers.computation()) 指定了订阅者在计算线程中接收数据。
 * subscribeOn(), observeOn() 进行线程调度
 * compose()进行组合操作
 * compose()：用于组合多个操作符，创建一个自定义的操作符。通过使用 compose()，你可以将一系列操作符封装为一个可重复使用的操作符，以便在多个 Observable 上进行复用。
 * observeOn(AndroidSchedulers.mainThread()) 指定了订阅者在主线程中接收数据。compose()将自定义的操作符应用于 Observable
 * subscribeOn() 和 observeOn() 是 RxJava 中的两个关键操作符，用于控制 Observable 的执行线程和订阅者的接收线程。
 * subscribeOn() 操作符用于指定 Observable 的执行线程。它会影响整个链式操作符中的执行线程。
 * 当使用 subscribeOn() 时，Observable 的创建和订阅操作将在指定的线程中执行。这意味着 Observable 的数据流将在指定的线程中发射，并在该线程中执行所有操作符。
 * 如果多次使用 subscribeOn()，只有第一个 subscribeOn() 起作用，后续的将被忽略。
 * observeOn() 操作符用于指定订阅者接收数据的线程。它可以在链式操作符中的任何位置使用，以改变后续操作符和订阅者的执行线程。
 * 当使用 observeOn() 时，它会影响 observeOn() 之后的操作符和订阅者的执行线程。
 * 总结一下，subscribeOn() 用于指定 Observable 的执行线程，observeOn() 用于指定订阅者接收数据的线程。这两个操作符可以一起使用，以实现不同的线程切换需求。
 * 通过使用这些操作符，你可以灵活地控制 Observable 的执行线程和订阅者的接收线程，并实现自定义的操作符组合。
 * Author: 张松
 */
public class NetworkHelper {

    /* ********************************** 共用 *************************************/

    /**
     * 单例封装帮助类全局变量
     **/
    private static volatile NetworkHelper networkHelperInstance;

    /**
     * 定义retrofit全局网络配置外部接口
     */
    private INetworkConfig iNetworkGlobalConfig;

    /* ********************************** Retrofit *************************************/

    /**
     * Retrofit全局变量
     */
    private Retrofit mRetrofit;

    /**
     * 单个网络配置的Map集合，为了兼容多服务器URL
     */
    private final Map<String, INetworkConfig> iNetworkSingleConfigMap = new HashMap<>();

    /**
     * Retrofit的Map集合，为了兼容多服务器URL
     */
    private final Map<String, Retrofit> retrofitMap = new HashMap<>();

    /* ********************************** Okhttp *************************************/

    /**
     * 声明OkHttpClient全部变量
     */
    private OkHttpClient mClient;

    /**
     * OkHttpClient的Map集合，为了兼容多服务器URL
     */
    private final Map<String, OkHttpClient> okhttpClientMap = new HashMap<>();

    /**
     * 无参构造
     */
    private NetworkHelper() {
        mRetrofit = null;
        mClient = null;
    }

    /**
     * 判空创建网络配置帮助类单例
     *
     * @return NetworkHelper单例对象
     */
    public static NetworkHelper getInstance() {
        if (networkHelperInstance == null) {//考虑效率问题
            synchronized (NetworkHelper.class) {
                if (networkHelperInstance == null) {//考虑多个线程问题
                    networkHelperInstance = new NetworkHelper();
                }
            }
        }
        return networkHelperInstance;
    }

    /* ********************************** 集合数据 *************************************/

    /**
     * 获取单个retrofit网络配置Map集合
     *
     * @return INetWorkConfig对象集合
     */
    public Map<String, INetworkConfig> getNetworkSingleConfigMap() {
        return iNetworkSingleConfigMap;
    }

    /**
     * 获取全局网络配置
     *
     * @return INetWorkConfig
     */
    public INetworkConfig getNetworkGlobalConfig() {
        return iNetworkGlobalConfig;
    }

    /**
     * 获取Retrofit的Map集合
     *
     * @return Retrofit对象集合
     */
    public Map<String, Retrofit> getRetrofitMap() {
        return retrofitMap;
    }

    /**
     * 获取OkHttpClient的Map集合
     *
     * @return OkHttpClient对象集合
     */
    public Map<String, OkHttpClient> getOkhttpClientMap() {
        return okhttpClientMap;
    }

    /**
     * 清除全部网络配置缓存数据
     */
    public void clearAllNetworkConfigCache() {
        getInstance().getNetworkSingleConfigMap().clear();
        iNetworkGlobalConfig = null;
        getInstance().getRetrofitMap().clear();
        getInstance().getOkhttpClientMap().clear();
    }

    /* ********************************** 注册网络配置与校验注册：全局配置、单次配置 *************************************/

    /**
     * 检查是否注册网络配置项
     *
     * @param baseUrl 服务器URL
     * @param config  网络配置
     */
    private void checkNetworkConfig(String baseUrl, INetworkConfig config) {
        if (config == null) {
            throw new IllegalStateException("【" + baseUrl + "】：This baseUrl must implement the INetworkConfig interface, " +
                    "and call getApiServiceClass(baseUrl,service,singleConfig) to pass in the implementation class. Alternatively, " +
                    "please register the global configuration in the Application, and the framework will automatically reference it and provide it for retrofit to use!");

        }
    }

    /**
     * 注册Okhttp网络全局配置，接口传递的数据直接使用
     * 全局配置建议使用在Application中注册
     * 多次使用registerNetworkGlobalConfig()全局注册，旧INetworkConfig直接替换为新INetworkConfig
     *
     * @param globalConfig INetWorkConfig全局配置
     */
    public void registerNetworkGlobalConfig(INetworkConfig globalConfig) {
        LogUtil.i("NetworkHelper", "registerNetworkGlobalConfig: 注册全局网络配置");
        getInstance().iNetworkGlobalConfig = globalConfig;
    }

    /**
     * 此单次配置方法在后续版本有可能会被移除
     * 注册网络单次配置，采用Map集合存取配置对象，实现多URL情况
     * 当网络单次配置没有注册时，会调用全局配置。
     * 单次配置建议使用在Service中注册（Service中实现INetworkConfig接口完成单次配置）
     * 多次使用registerNetworkSingleConfig()全局注册，旧INetworkConfig的baseUrl匹配成功后替换新INetworkConfig为新value值，不匹配意味着之前无此baseUrl数据对应的网络配置
     *
     * @param singleConfig INetWorkConfig配置
     */
    @Deprecated
    public void registerNetworkSingleConfig(String baseUrl, INetworkConfig singleConfig) {
        LogUtil.i("NetworkHelper", "registerNetworkSingleConfig: " + baseUrl + "注册单次网络配置");
        getInstance().getNetworkSingleConfigMap().put(baseUrl, singleConfig);
    }

    /* ********************************** Retrofit配置 *************************************/

    /**
     * 一个baseUrl只能对应一个INetworkConfig配置，若同个baseUrl调用多次getRetrofit()，会先从map集合中get，有就取，没有就创建且put，实现复用
     * 获取Retrofit实例：配置BaseUrl、配置client、配置转换器
     * 网络配置引用优先级：iNetworkSingleConfigMap（单次注册） > iNetworkGlobalConfig（全局注册）>
     * 若使用registerNetworkGlobalConfig()注册，则调用getNetworkGlobalConfig()获取全局网络配置
     * 若使用registerNetworkSingleConfig()注册，则调用getApiServiceClass(baseUrl, isEnableCookieStore,service)
     *
     * @param baseUrl       服务器URL
     * @param networkConfig 网络配置
     * @return Retrofit对象
     */
    private Retrofit getRetrofit(String baseUrl, INetworkConfig networkConfig) {
        //服务器URL为空时程序关闭打印异常
        if (TextUtils.isEmpty(baseUrl)) throw new IllegalStateException("baseUrl can not be null!");

        //判断本次请求的服务器URL在Map集合中是否已注册有网络配置，如果已注册就将此含有配置属性的Retrofit对象返回使用
        if (getRetrofitMap().get(baseUrl) != null) {
            LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "——>Retrofit已存在创建，正在引用中..");
            return getRetrofitMap().get(baseUrl);
        } else {
            LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "——>Retrofit未创建");
        }

        //此处是使用全局网络配置、单个网络配置的关键之处
        if (networkConfig == null) {   //netWorkConfig == null 意味着调用getRetrofit()时未使用参数传递注册网络配置
            LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前未使用参数传递注册网络配置，待判断单次+全局");
            //先根据baseUrl查询是否已注册对应单次配置，若已注册单次配置直接赋值给netWorkConfig变量提供后续使用
            networkConfig = getNetworkSingleConfigMap().get(baseUrl);
            //如果已注册单次配置，netWorkConfig!=null
            if (networkConfig != null) { //有单次，直接用单次
                LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前复用已有单次网络配置");
            } else {//无单次，先判断全局
                LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前未使用单次网络配置，待查询全局");
                networkConfig = getNetworkGlobalConfig();
                if (networkConfig != null) { //无单次+有全局 netWorkConfig!=null
                    LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前使用全局网络配置");
                } else { //无单此+无全局 netWorkConfig==null
                    LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前无任何网络配置，请先实现INetworkConfig接口，再调用getApiServiceClass(传入接口实现类)");
                }
            }
        } else {
            LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "当前已使用参数传递注册单次网络配置");
        }

        //对引用网络配置判空校验，未注册配置程序关闭打印异常，反知继续下一步
        checkNetworkConfig(baseUrl, networkConfig);

        LogUtil.i("NetworkHelper", "getRetrofit: " + baseUrl + "成功引用网络配置");

        //将接口传递来的网络配置参数装载Retrofit，生成Service
        if (mRetrofit == null) {
            //开始构建Retrofit
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl) //服务器URL
                    .client(getHttpClient(baseUrl, networkConfig));//设置使用okhttp网络请求，加载Okhttp已配置的网络参数

            //开启实体转换器必须设置模式
            if (Objects.requireNonNull(networkConfig).setConverterFactoryMode() == null) {
                throw new IllegalStateException("converter factory mode can not be null!");
            }
            //匹配模式设置属性
            switch (networkConfig.setConverterFactoryMode()) {
                case GSON:
                    builder.addConverterFactory(GsonConverterFactory.create());
                    LogUtil.i("NetworkHelper", "getRetrofit: 使用Gson转换器");
                    break;
                case JACKSON:
                    builder.addConverterFactory(JacksonConverterFactory.create());
                    LogUtil.i("NetworkHelper", "getRetrofit: 使用jackson转换器");
                    break;
                case SCALARS:
                    builder.addConverterFactory(ScalarsConverterFactory.create());
                    LogUtil.i("NetworkHelper", "getRetrofit: 使用scalars转换器");
                    break;
                case MOSHI:
                    builder.addConverterFactory(MoshiConverterFactory.create());
                    LogUtil.i("NetworkHelper", "getRetrofit: 使用moshi转换器");
                    break;
                case SIMPLE_XML:
                    builder.addConverterFactory(SimpleXmlConverterFactory.create());
                    LogUtil.i("NetworkHelper", "getRetrofit: 使用simplexml转换器");
                    break;
                case CUSTOM:
                    Converter.Factory factory = networkConfig.setCustomConverterFactory();
                    if (factory != null) {
                        builder.addConverterFactory(factory);
                        LogUtil.i("NetworkHelper", "getRetrofit: 使用定制转换器");
                    }
                    break;
            }

            //启用RxJava适配器
            //添加回调库
            builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create());

            //结束并构建Retrofit对象
            mRetrofit = builder.build();

            //重用网络配置：将本次已引用网络配置的Retrofit对象存入Map集合，下次根据key服务器URL获取对应的Retrofit对象值
            getRetrofitMap().put(baseUrl, mRetrofit);

            //重用网络配置：将本次已引用网络配置的单次注册的网络配置存入Map集合
            getNetworkSingleConfigMap().put(baseUrl, networkConfig);
        }
        return mRetrofit;
    }

    /**
     * 创建Retrofit的Class请求接口，默认使用全局网络配置
     *
     * @param baseUrl 服务器URL
     * @param service 服务class
     * @param <C>     泛型
     * @return Retrofit对象
     */
    public <C> C getApiServiceClass(String baseUrl, Class<C> service) {
        return getInstance().getRetrofit(baseUrl, null).create(service);
    }

    /**
     * 创建Retrofit的Class请求接口，指定使用单次网络配置
     * 首次配置注册后可重复调用，后续网络请求接口时，不需要再次创建retrofit、okhttp、拦截器等实例，直接从map取出来借助RxJava线程调度后返回数据UI显示
     *
     * @param baseUrl      服务器URL
     * @param service      服务class
     * @param singleConfig 单次网络配置，多baseUrl情况使用，例如：线上正式环境、线上测试环境、本地开发环境、其它环境等。
     * @param <C>          泛型
     * @return Retrofit对象
     */
    public <C> C getApiServiceClass(String baseUrl, Class<C> service, INetworkConfig singleConfig) {
        return getInstance().getRetrofit(baseUrl, singleConfig).create(service);
    }

    /* ********************************** Okhttp配置 *************************************/

    /**
     * 设置日志拦截器打印日志：
     * NONE 无
     * BASIC  响应行
     * HEADERS 响应行+头、
     * BODY 响应行+头+体
     *
     * @return 日志拦截器实例
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //创建okhttp3日志拦截器实例
        return new HttpLoggingInterceptor(strLogMsg -> {
            //设置打印retrofit日志前缀
            LogUtil.i("okpLog", strLogMsg);
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    /**
     * 获取和配置OkHttpClient：连接超时时间、读取和写入超时时间、自定义拦截器、请求日志拦截器
     *
     * @param baseUrl       服务器URL
     * @param networkConfig 网络配置
     * @return OkHttpClient对象
     */
    private OkHttpClient getHttpClient(String baseUrl, INetworkConfig networkConfig) {
        //服务器URL为空时程序关闭打印异常
        if (TextUtils.isEmpty(baseUrl)) throw new IllegalStateException("baseUrl can not be null!");

        //判断本次请求的服务器URL在Map集合中是否已注册有Okhttp对象，如果已创建对象返回给Retrofit使用
        if (getOkhttpClientMap().get(baseUrl) != null) {
            LogUtil.i("NetworkHelper", "getHttpClient: " + baseUrl + "——>OkHttpClient已存在创建，正在引用中..");
            return getOkhttpClientMap().get(baseUrl);
        } else {
            LogUtil.i("NetworkHelper", "getHttpClient: " + baseUrl + "——>OkHttpClient未创建");
        }

        //此处需对赋值引用全局配置进行判空校验，无全局网络配置程序关闭打印异常，反知即可进行下一步配置
        checkNetworkConfig(baseUrl, networkConfig);

        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //请求错误后重试
            builder.retryOnConnectionFailure(networkConfig.setIsEnableRetryOnConnection());
            // 请求连接超时时间，时间数值为0就使用默认值，时间数值不为0就使用Application注册传递过来的时间数值
            builder.connectTimeout(networkConfig.setConnectTimeoutMills() != 0
                    ? networkConfig.setConnectTimeoutMills()
                    : 40 * 1000L, TimeUnit.MILLISECONDS); //默认请求连接超时时间
            // 数据读取超时时间，时间数值为0就使用默认值，时间数值不为0就使用Application注册传递过来的时间数值
            builder.readTimeout(networkConfig.setReadTimeoutMills() != 0
                    ? networkConfig.setReadTimeoutMills()
                    : 40 * 1000L, TimeUnit.MILLISECONDS); //默认数据读取超时时间
            // 数据写入超时时间，时间数值为0就使用默认值，时间数值不为0就使用Application注册传递过来的时间数值
            builder.writeTimeout(networkConfig.setWriteTimeoutMills() != 0
                    ? networkConfig.setWriteTimeoutMills()
                    : 40 * 1000L, TimeUnit.MILLISECONDS); //默认数据写入超时时间

            //设置Cookie存取模式
            if (!networkConfig.setEnableCookieStore()) {
                LogUtil.i("NetworkHelper", "getRetrofit: Current not using cookie store!");
            } else {
                LogUtil.i("NetworkHelper", "getRetrofit: Current yes using cookie store!");
                //开启Cookie存取必须设置模式
                if (networkConfig.setCookieStoreMode() == null) {
                    throw new IllegalStateException("cookie store mode can not be null!");
                }
                //匹配模式设置属性
                switch (networkConfig.setCookieStoreMode()) {
                    case SP:
                        builder.cookieJar(new CookieJarImpl(new SpCookieStore(networkConfig.setApplicationContext())));
                        LogUtil.i("NetworkHelper", "getHttpClient: 当前Cookie存取模式是SharedPreferences");
                        break;
                    case MEMORY:
                        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
                        LogUtil.i("NetworkHelper", "getHttpClient: 当前Cookie存取模式是Memory");
                        break;
                    case DB:
                        // TODO: 2023/8/31 待完善DB模式存取Cookie
                        builder.cookieJar(new CookieJarImpl(new DBCookieStore()));
                        LogUtil.i("NetworkHelper", "getHttpClient: 当前Cookie存取模式是DB");
                        break;
                    case CUSTOM:
                        //自定义
                        CookieJar cookieJar = networkConfig.setCustomCookieStore();
                        if (cookieJar != null) {
                            builder.cookieJar(cookieJar);
                            LogUtil.i("NetworkHelper", "getHttpClient: 当前Cookie存取模式是Custom");
                        } else {
                            throw new IllegalStateException("You must first create a class to implement the CookieJar interface of Okhttp before completing the customization of business requirements!");
                        }
                        break;
                }
            }

            //判断是否开启缓存 + 设置缓存时间
            if (networkConfig.setIsEnableCache()) {
                builder.addInterceptor(new InterceptorImpl(
                        new CachesInterceptor(networkConfig.setApplicationContext(), networkConfig.setCacheMaxAgeTimeUnitSeconds() != 0
                                ? networkConfig.setCacheMaxAgeTimeUnitSeconds()
                                : 60 * 60, networkConfig.setCacheMaxStaleTimeUnitSeconds() != 0 //缓存有效时间，过期就向服务器重新请求
                                ? networkConfig.setCacheMaxStaleTimeUnitSeconds()
                                : 60 * 60 * 24)) //缓存过期后，陈旧秒数不超过max-stale仍然可以使用缓存，如果max-stale后面没有值，无论过期多久都可以使用缓存
                );

                //构建设置设置缓存目录和缓存大小为10MB
                builder.cache(new Cache(new File(
                        networkConfig.setApplicationContext().getCacheDir().getAbsolutePath(), "MyNetworkCache"), 10 * 1024 * 1024)
                );
                LogUtil.i("NetworkHelper", "已启用Okhttp缓存：缓存大小==" + 10 * 1024 * 1024);
            } else {
                LogUtil.i("NetworkHelper", "未启用Okhttp缓存");
            }

            //拦截器时开始请求时才会触发

            //响应拦截器
            builder.addInterceptor(new InterceptorImpl(new ResponseInterceptor()));
            //设置请求头
            builder.addInterceptor(new InterceptorImpl(new HeadersInterceptor(networkConfig.setHeaderParameters())));
            //设置URL公共参数
            builder.addInterceptor(new InterceptorImpl(new UrlParameterInterceptor(networkConfig.setUrlParameter())));

            //接口传递：遍历设置添加定制拦截器，拦截交互数据
            InterceptorHandler[] interceptorHandlers = networkConfig.setCustomInterceptor();
            if (interceptorHandlers != null) {
                for (InterceptorHandler interceptorHandler : interceptorHandlers) {
                    //依次配置拦截器到Okhttp中
                    builder.addInterceptor(new InterceptorImpl(interceptorHandler));
                    LogUtil.i("NetworkHelper", "添加定制拦截器（接口传递方式）：" + interceptorHandler.getClass().getSimpleName());
                }
            }

            //非接口传递：遍历设置添加创建拦截器数组，如通过拦截器添加统一请求头等。
            Interceptor[] interceptors = networkConfig.setInterceptors();
            if (interceptors != null) {
                for (Interceptor interceptor : interceptors) {
                    //依次配置拦截器到Okhttp中
                    builder.addInterceptor(interceptor);
                    LogUtil.i("NetworkHelper", "添加定制拦截器（非接口传递方式）：" + interceptor.getClass().getSimpleName());
                }
            }

            //判断是否开启打印默认日志
            //根据APK打包类型(开发版或发布版)判定当前应用程序是否启用日志拦截器打印请求日志。PS：开发版启用打印，发布版禁用打印。
            if (networkConfig.setIsEnableOkpDefaultPrintLog()) {
                builder.addNetworkInterceptor(getHttpLoggingInterceptor());
                LogUtil.i("NetworkHelper", "已启用Okhttp默认日志打印");
            } else {
                LogUtil.i("NetworkHelper", "未启用Okhttp默认日志打印");
            }

            //开始构建OkhttpClient对象
            mClient = builder.build();

            //重用网络配置：将本次已引用网络配置的OkhttpClient对象存入Map集合，下次根据key服务器URL获取对应的OkhttpClient对象值
            getOkhttpClientMap().put(baseUrl, mClient);

            //重用网络配置：将本次已引用网络配置的单次注册的网络配置存入Map集合
            getNetworkSingleConfigMap().put(baseUrl, networkConfig);
        }
        return mClient;
    }

    /* ********************************** RxJava配置：线程调用 + 异常处理变换（详见本库rxjava/function、rxjava/transformer）*************************************/
}

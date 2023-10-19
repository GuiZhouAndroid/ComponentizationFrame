package zsdev.work.lib.support.network.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created: by 2023-09-04 22:38
 * Description: Cookie内存存取类。获取响应体中的Cookie后进行持久化并返回给Okhttp配置。
 * 临时（App应用程序退出即为Cookie数据丢失）：Map集合、Java类(内存)
 * 常用于保存用户登录成功后服务器返回的token凭证，下次访问后端其它接口时作为身份验证鉴权。
 * Author: 张松
 */
public class MemoryCookieStore implements CookieStore {

    /**
     * 存取Cookie数据的Map集合
     */
    private final HashMap<String, List<Cookie>> allCookies = new HashMap<>();

    /**
     * 存储Cookie
     *
     * @param url            Sp的key值url
     * @param newCookiesList 新Cookie集合
     */
    @Override
    public void addCookieByHttpUrl(HttpUrl url, List<Cookie> newCookiesList) {
        //先根据本次请求url从map取Cookie旧数据
        List<Cookie> oldCookiesList = allCookies.get(url.host());
        if (oldCookiesList != null) { //此url有旧Cookie，就要覆盖为新cookies
            Iterator<Cookie> itNewCookies = newCookiesList.iterator();
            Iterator<Cookie> itOldCookies = oldCookiesList.iterator();
            //遍历新Cookie集合，取出新name
            while (itNewCookies.hasNext()) {
                String newCookieName = itNewCookies.next().name();
                //遍历新Cookie集合，取出旧name
                while (itOldCookies.hasNext()) {
                    String oldCookieName = itOldCookies.next().name();
                    //新旧Name对比，若Name匹配成功，即是同个Cookie信息，那么将旧Cookie数据清空
                    if (newCookieName.equals(oldCookieName)) {
                        //无旧Cookie数据
                        itOldCookies.remove();
                    }
                }
            }
            //用oldCookies空集合，装载新Cookie数据
            oldCookiesList.addAll(newCookiesList);
            //存map
            allCookies.put(url.host(), oldCookiesList);
        } else { //此url无旧Cookie，直接存map
            allCookies.put(url.host(), newCookiesList);
        }
    }

    /**
     * 通过Sp的key值获取对应Cookie
     *
     * @param url Sp的key值url
     * @return Cookie集合
     */
    @Override
    public List<Cookie> getCookieByHttpUrl(HttpUrl url) {
        List<Cookie> cookies = allCookies.get(url.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCookies.put(url.host(), cookies);
        }
        return cookies;
    }

    /**
     * 获取全部Cookie数据
     *
     * @return cookie集合
     */
    @Override
    public List<Cookie> getAllCookies() {
        List<Cookie> cookies = new ArrayList<>();
        Set<String> httpUrls = allCookies.keySet();
        for (String url : httpUrls) {
            cookies.addAll(Objects.requireNonNull(allCookies.get(url)));
        }
        return cookies;
    }

    /**
     * 删除单个Cookie
     *
     * @param url    Sp的key值url
     * @param cookie Sp的value值
     * @return 删除结果
     */
    @Override
    public boolean removeCookieByHttpUrl(HttpUrl url, Cookie cookie) {
        List<Cookie> cookies = allCookies.get(url.host());
        if (cookie != null) {
            return Objects.requireNonNull(cookies).remove(cookie);
        }
        return false;
    }

    /**
     * 删除全部Cookie数据
     *
     * @return 删除结果
     */
    @Override
    public boolean removeAllCookies() {
        allCookies.clear();
        return true;
    }
}

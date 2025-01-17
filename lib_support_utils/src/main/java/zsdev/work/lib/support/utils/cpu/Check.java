package zsdev.work.lib.support.utils.cpu;

import java.util.Collection;
import java.util.Map;

/**
 * Created: by 2023-09-26 10:11
 * Description: cpu辅助判断
 * Author: 张松
 */
public class Check {

    public static boolean isEmpty(CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isEmpty(Object[] os) {
        return isNull(os) || os.length == 0;
    }

    public static boolean isEmpty(Collection<?> l) {
        return isNull(l) || l.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> m) {
        return isNull(m) || m.isEmpty();
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}

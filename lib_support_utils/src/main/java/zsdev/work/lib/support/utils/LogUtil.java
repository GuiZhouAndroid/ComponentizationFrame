package zsdev.work.lib.support.utils;


import android.util.Log;

/**
 * Created: by 2023-08-11 23:49
 * Description: 日志工具类
 * VERBOSE：最低级别，用于输出任意级别的日志信息。
 * DEBUG：用于输出调试信息，通常情况下不会显示在生产版本中。
 * INFO：用于输出一些比较重要的信息，比如应用程序启动时的版本号等。
 * WARN：用于输出一些警告信息，表示可能会出现一些问题，但是不影响程序的正常运行。
 * ERROR：用于输出错误信息，表示发生了一些错误或异常情况。
 * Author: 张松
 */
public class LogUtil {

    private static final String TAG = "LogUtil";
    private static boolean LOG_ENABLE = true;
    private static boolean DETAIL_ENABLE = true;

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();

        if (DETAIL_ENABLE) {
            final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
            buffer.append("[ ");
            buffer.append(Thread.currentThread().getName());
            buffer.append(": ");
            buffer.append(stackTraceElement.getFileName());
            buffer.append(": ");
            buffer.append(stackTraceElement.getLineNumber());
            buffer.append(": ");
            buffer.append(stackTraceElement.getMethodName());
        }

        buffer.append("() ] _____ ");

        buffer.append(msg);

        return buffer.toString();
    }

    /**
     * LOG_ENABLE 设置是否显示Log
     * DETAIL_ENABLE 设置是否显示详细Log
     *
     * @param enable true-显示/显示详细 false-不显示 /不显示详细
     */
    public static void setLogEnable(boolean enable) {
        LOG_ENABLE = enable;
        DETAIL_ENABLE = enable;
    }

    /**
     * verbose log
     *
     * @param msg log msg
     */
    public static void v(String msg) {
        if (LOG_ENABLE) {
            Log.v(TAG, buildMsg(msg));
        }
    }

    /**
     * verbose log
     *
     * @param tag tag
     * @param msg log msg
     */
    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.v(tag, buildMsg(msg));
        }
    }

    /**
     * debug log
     *
     * @param msg log msg
     */
    public static void d(String msg) {
        if (LOG_ENABLE) {
            Log.d(TAG, buildMsg(msg));
        }
    }

    /**
     * debug log
     *
     * @param tag tag
     * @param msg log msg
     */
    public static void d(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.d(tag, buildMsg(msg));
        }
    }

    /**
     * info log
     *
     * @param msg log msg
     */
    public static void i(String msg) {
        if (LOG_ENABLE) {
            Log.i(TAG, buildMsg(msg));
        }
    }

    /**
     * info log
     *
     * @param tag tag
     * @param msg log msg
     */
    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.i(tag, buildMsg(msg));
        }
    }

    /**
     * warning log
     *
     * @param msg log msg
     */
    public static void w(String msg) {
        if (LOG_ENABLE) {
            Log.w(TAG, buildMsg(msg));
        }
    }

    /**
     * warning log
     *
     * @param msg log msg
     * @param e   exception
     */
    public static void w(String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.w(TAG, buildMsg(msg), e);
        }
    }

    /**
     * warning log
     *
     * @param tag tag
     * @param msg log msg
     */
    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.w(tag, buildMsg(msg));
        }
    }

    /**
     * warning log
     *
     * @param tag tag
     * @param msg log msg
     * @param e   exception
     */
    public static void w(String tag, String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.w(tag, buildMsg(msg), e);
        }
    }

    /**
     * error log
     *
     * @param msg log msg
     */
    public static void e(String msg) {
        if (LOG_ENABLE) {
            Log.e(TAG, buildMsg(msg));
        }
    }

    /**
     * error log
     *
     * @param msg log msg
     * @param e   exception
     */
    public static void e(String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.e(TAG, buildMsg(msg), e);
        }
    }

    /**
     * error log
     *
     * @param tag tag
     * @param msg msg
     */
    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.e(tag, buildMsg(msg));
        }
    }

    /**
     * error log
     *
     * @param tag tag
     * @param msg log msg
     * @param e   exception
     */
    public static void e(String tag, String msg, Exception e) {
        if (LOG_ENABLE) {
            Log.e(tag, buildMsg(msg), e);
        }
    }
}
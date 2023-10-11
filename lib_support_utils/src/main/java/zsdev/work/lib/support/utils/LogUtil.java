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
    private static boolean IS_SHOW_SIMPLE_LOG = true;
    private static boolean IS_SHOW_DETAIL_LOG = true;

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();

        if (IS_SHOW_DETAIL_LOG) {
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
     * @param isShowSimpleLogMsg 设置是否显示简单Log true-显示 false-不显示
     * @param isShowDetailLogMsg 设置是否显示详细Log true-显示详细 false-不显示详细
     */
    public static void setLogEnable(boolean isShowSimpleLogMsg, boolean isShowDetailLogMsg) {
        IS_SHOW_SIMPLE_LOG = isShowSimpleLogMsg;
        IS_SHOW_DETAIL_LOG = isShowDetailLogMsg;
    }

    /**
     * verbose log
     *
     * @param msg log msg
     */
    public static void v(String msg) {
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
            Log.v(tag, buildMsg(msg));
        }
    }

    /**
     * debug log
     *
     * @param msg log msg
     */
    public static void d(String msg) {
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
            Log.d(tag, buildMsg(msg));
        }
    }

    /**
     * info log
     *
     * @param msg log msg
     */
    public static void i(String msg) {
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
            Log.i(tag, buildMsg(msg));
        }
    }

    /**
     * warning log
     *
     * @param msg log msg
     */
    public static void w(String msg) {
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
            Log.w(tag, buildMsg(msg), e);
        }
    }

    /**
     * error log
     *
     * @param msg log msg
     */
    public static void e(String msg) {
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
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
        if (IS_SHOW_SIMPLE_LOG) {
            Log.e(tag, buildMsg(msg), e);
        }
    }
}
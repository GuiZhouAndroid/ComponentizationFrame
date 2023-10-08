package zsdev.work.lib.support.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created: by 2023-10-08 20:47
 * Description: Toast工具类
 * Author: 张松
 */
public class ToastUtil {
    /**
     * 显示short message
     *
     * @param context 全局context
     * @param resId   string string资源id
     */
    public static void showShort(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示short message
     *
     * @param context 全局context
     * @param message 显示msg
     */
    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示short message
     *
     * @param context      全局context
     * @param charSequence 显示msg
     */
    public static void showShort(Context context, CharSequence charSequence) {
        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示long message
     *
     * @param context 全局context
     * @param resId   string string资源id
     */
    public static void showLong(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示long message
     *
     * @param context 全局context
     * @param message 显示msg
     */
    public static void showLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示long message
     *
     * @param context      全局context
     * @param charSequence 显示msg
     */
    public static void showLong(Context context, CharSequence charSequence) {
        Toast.makeText(context, charSequence, Toast.LENGTH_LONG).show();
    }
}

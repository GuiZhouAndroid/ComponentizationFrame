package zsdev.work.lib.support.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created: by 2023-10-08 20:58
 * Description: 反射工具类
 * Author: 张松
 */
public class ReflectUtil {
    //==========================================================================
    // Constants
    //==========================================================================

    //==========================================================================
    // Fields
    //==========================================================================

    //==========================================================================
    // Constructors
    //==========================================================================

    //==========================================================================
    // Getters
    //==========================================================================

    //==========================================================================
    // Setters
    //==========================================================================

    //==========================================================================
    // Methods
    //==========================================================================
    public static <T> T invokeStatic(Class<T> returnType, Class<?> receiverType, String methodName,
                                     Class<?>[] paramTypes, Object[] params) {
        return invoke(returnType, receiverType, methodName, paramTypes, receiverType, params, false);
    }

    public static <T> T invokeStatic(Class<T> returnType, Class<?> receiverType, String methodName,
                                     Class<?>[] paramTypes, Object[] params, boolean requestAccessiblity) {
        return invoke(returnType, receiverType, methodName, paramTypes, receiverType, params, requestAccessiblity);
    }

    public static <T> T invoke(Class<T> returnType, Class<?> receiverType, String methodName, Class<?>[] paramTypes,
                               Object receiver, Object[] params) {
        return invoke(returnType, receiverType, methodName, paramTypes, receiver, params, false);
    }

    @SuppressWarnings("unchecked")
    public static <T> T invoke(Class<T> returnType, Class<?> receiverType, String methodName, Class<?>[] paramTypes,
                               Object receiver, Object[] params, boolean requestAccessiblity) {
        T res = null;
        try {
            Method method = receiverType.getDeclaredMethod(methodName, paramTypes);
            if (requestAccessiblity) {
                method.setAccessible(true);
            }
            res = (T) method.invoke(receiver, params);
        } catch (SecurityException e) {
               LogUtil.e(e.toString());
        } catch (NoSuchMethodException e) {
               LogUtil.e(e.toString());
        } catch (IllegalArgumentException e) {
               LogUtil.e(e.toString());
        } catch (IllegalAccessException e) {
               LogUtil.e(e.toString());
        } catch (InvocationTargetException e) {
               LogUtil.e(e.toString());
        } catch (Exception e) {
               LogUtil.e(e.toString());
        } catch (Throwable e) {
               LogUtil.e(e.toString());
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Class<T> fieldType, Class<?> receiverType, String fieldName, Object receiver,
                                 boolean requestAccessiblity) {
        T res = null;
        try {
            Field f = receiverType.getDeclaredField(fieldName);
            if (requestAccessiblity) {
                f.setAccessible(true);
            }
            res = (T) f.get(receiver);
        } catch (SecurityException e) {
               LogUtil.e(e.toString());
        } catch (NoSuchFieldException e) {
               LogUtil.e(e.toString());
        } catch (IllegalArgumentException e) {
               LogUtil.e(e.toString());
        } catch (IllegalAccessException e) {
            LogUtil.e(e.toString());
        } catch (Throwable e) {
            LogUtil.e(e.toString());
        }

        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> T setValue(Class<T> fieldType, Class<?> receiverType, String fieldName, Object receiver, T value,
                                 boolean requestAccessiblity) {
        T res = null;
        try {
            Field f = receiverType.getDeclaredField(fieldName);
            if (requestAccessiblity) {
                f.setAccessible(true);
            }
            f.set(receiver, value);
        } catch (SecurityException e) {
            LogUtil.e(e.toString());
        } catch (NoSuchFieldException e) {
            LogUtil.e(e.toString());
        } catch (IllegalArgumentException e) {
            LogUtil.e(e.toString());
        } catch (IllegalAccessException e) {
            LogUtil.e(e.toString());
        } catch (Throwable e) {
            LogUtil.e(e.toString());
        }

        return res;
    }
    //==========================================================================
    // Inner/Nested Classes
    //==========================================================================
}

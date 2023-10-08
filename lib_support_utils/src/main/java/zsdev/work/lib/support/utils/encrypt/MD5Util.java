package zsdev.work.lib.support.utils.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import zsdev.work.lib.support.utils.LogUtil;

/**
 * Created: by 2023-09-26 10:14
 * Description:  MD5加密 不可逆
 * Author: 张松
 */
public class MD5Util {

    private static final String TAG = "MD5Util";

    private MD5Util() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * MD5加密
     * StringBuilder不支持并发操作，线性不安全的，不适合多线程中使用。但其在单线程中的性能比StringBuffer高。
     *
     * @param str
     * @return
     */
    public static String encryptMD5ForBuilder(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] cipher = digest.digest();

            for (byte b : cipher) {
                String hexStr = Integer.toHexString(b & 0xff);
                builder.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * MD5加密
     * StringBuffer支持并发操作，线性安全的，适合多线程中使用。
     *
     * @param str
     * @return
     */
    public static String encryptMD5ForBuffer(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] cipher = digest.digest();

            for (byte b : cipher) {
                String hexStr = Integer.toHexString(b & 0xff);
                buffer.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * md5加密
     *
     * @param plainText 待加密字符串
     * @return 加密后32位字符串
     */
    public static String getMd5(String plainText) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, "getMd5 error", e);
            return null;
        }

    }
}


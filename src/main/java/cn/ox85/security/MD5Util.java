package cn.ox85.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author alec zhang
 */
public class MD5Util {

    public static final String MD5(String value) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        return MD5(value.getBytes("UTF-8"));
    }

    public static final String MD5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(bytes);
        byte[] md5 = digest.digest();
        StringBuilder sb = new StringBuilder(32);
        for (byte b : md5) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}

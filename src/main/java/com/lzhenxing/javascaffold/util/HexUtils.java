package com.lzhenxing.javascaffold.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: HexUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/11
 */
public class HexUtils {
    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    private static final int SHIFT    = 0x0F;
    private static final String UTF_8 = "utf-8";


    /**
     *
     * stringHex:16进制编码转为字符串  😍
     *
     * @param hex
     * @return
     * @since JDK 1.7
     */
    public static String stringHex(String hex){
        return stringHex(hex, HexUtils.UTF_8);
    }


    /**
     *
     * stringHex:16进制编码转为字符串
     *
     * @param hex
     * @return
     * @since JDK 1.7
     */
    public static String stringHex(String hex, String charset){
        byte[] bt = new byte[hex.length() / 2];
        for (int i = 0; i < bt.length; ++i) {
            bt[i] = (byte) (0xFF & Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16));
        }
        try {
            return new String(bt, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * hexString:将字符串转为16进制编码
     *
     * @param str
     * @param charset
     * @return
     * @since JDK 1.7
     */
    public static String hexString(String str, String charset){
        try {
            return str == null ? null : HexUtils.byteHexString(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * hexString:将字符串转为16进制编码
     *
     * @param str
     * @return
     * @since JDK 1.7
     */
    public static String hexString(String str){
        return HexUtils.hexString(str, HexUtils.UTF_8);
    }

    /**
     * 将字节数组转变为16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String byteHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            sb.append(HexUtils.byteHexString(bytes[i]));
        }
        return sb.toString();
    }


    /**
     *
     * byteToHeyString:字节数组转为16进制字符串
     *
     * @param bt
     * @return
     * @since JDK 1.7
     */
    public static String byteHexString(byte bt) {
        char[] ch = new char[2];
        ch[0] = HexUtils.digits[(bt >>> 4) & HexUtils.SHIFT];
        ch[1] = HexUtils.digits[bt & HexUtils.SHIFT];
        return new String(ch);
    }

    public static String textToMD5L32(String plainText) {
        String result = null;
        // 首先判断是否为空
        if (StringUtils.isBlank(plainText)) {
            return null;
        }
        try {
            // 首先进行实例化和初始化
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 得到一个操作系统默认的字节编码格式的字节数组
            byte[] btInput = plainText.getBytes();
            // 对得到的字节数组进行处理
            md.update(btInput);
            // 进行哈希计算并返回结果
            byte[] btResult = md.digest();
            // 进行哈希计算后得到的数据的长度
            StringBuffer sb = new StringBuffer();
            for (byte b : btResult) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return plainText;
        }
        return result;
    }
}

package com.lzhenxing.javascaffold.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by gary on 2016/12/25.
 */
public class Base64Utils {

    private static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    public static String string2base64(String string, boolean isUrl) {
        try {
            if (isUrl) {
                return Base64.getUrlEncoder().encodeToString(string.getBytes("utf-8"));
            } else {
                return Base64.getEncoder().encodeToString(string.getBytes("utf-8"));
            }

        } catch (UnsupportedEncodingException e) {
            logger.error("unsupport encoding exception");
            return null;
        }
    }

    public static String decodeBase64(String string, boolean isUrl) {

        byte[] asBytes;
        if (isUrl) {
            asBytes = Base64.getUrlDecoder().decode(string);
        } else {
            asBytes = Base64.getDecoder().decode(string);
        }
        try {
            return new String(asBytes, "utf-8");

        } catch (UnsupportedEncodingException e) {
            logger.error("unsupport encoding exception");
            return null;
        }
    }

    public static String encodeOrDecodePath(String dirPath, boolean isEncode) {
        StringBuilder builder = new StringBuilder();
        String[] arr = dirPath.split("/");
        for (int i = 1; i < arr.length; i++) {
            if (isEncode) {
                builder.append(string2base64(arr[i], true)).append("/");
            } else {
                builder.append(decodeBase64(arr[i], true)).append("/");
            }
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }
}

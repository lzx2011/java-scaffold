package com.lzhenxing.javascaffold.util;

import java.util.regex.Pattern;

/**
 * ClassName: UrlUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/6
 */
public class UrlUtils {

    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 修复url
     * @param url
     * @return
     */
    public static String fixUrl(String url){
        String newUrl;
        if(org.apache.commons.lang3.StringUtils.isNotBlank(url)){
            if (!url.startsWith("http")) {
                newUrl = "http:".concat(url);
            } else {
                newUrl = url;
            }

            if(Pattern.matches(REGEX_URL, newUrl)){
                return newUrl;
            }
        }

        return "";
    }

    public static void main(String[] args){
        String url= "https://img.xxx.com/imgextra/i4/2255708913/TB2NF_1cpXXXXb3XXXXXXXXXXXX_!!2255708913.jpg";

        System.out.println(fixUrl(url));
    }
}

package com.lzhenxing.javascaffold.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class FastJsonUtil {
    private FastJsonUtil() {
    }

    /**
     * @param obj
     * @return
     * @since JDK 1.6
     */
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * @param obj
     * @param prettyFormat
     * @return
     * @since JDK 1.6
     */
    public static String bean2FormatJson(Object obj, boolean prettyFormat) {
        return JSON.toJSONString(obj, prettyFormat);
    }

    /**
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     * @since JDK 1.6
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return JSON.parseObject(jsonStr, objClass);
    }

    /**
     * String 转 beanList
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> List<T> json2BeanList(String jsonStr, Class<T> objClass){
        return JSON.parseArray(jsonStr, objClass);
    }

    /**
     * @param jsonStr
     * @param typeReference
     * @param <T>
     * @return
     * @since JDK 1.6
     */
    public static <T> T json2Bean(String jsonStr, TypeReference<T> typeReference) {
        return JSON.parseObject(jsonStr, typeReference);
    }
  
}


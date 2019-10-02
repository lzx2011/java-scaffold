package com.lzhenxing.javascaffold.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.lzhenxing.javascaffold.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class FastJsonUtil {

    private static Logger logger = LoggerFactory.getLogger(FastJsonUtil.class);


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
     * @param <T>d
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

    /**
     * fastjson会默认循环引用处理成 $ref,这样如果有循环引用会报stackoverflow error
     * @param obj
     * @return
     */
    public static String toJSONStringWithoutNull(Object obj) {
        try {
            return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            return "json化异常：" + e.getMessage();
        }
    }

    /**
     * 获取某个Key的值，并转化为对象
     *
     * @param targetJson
     * @param key
     * @param returnType
     * @return
     */
    public static <T> T retrieveFromJson(String targetJson, String key, Class<T> returnType){
        if (StringUtils.isBlank(targetJson)){
            return null;
        }

        List<Map<String, String>> list = null;
        try{
            list = JSON.parseObject(targetJson, new TypeReference<List<Map<String, String>>>(){
            }, Feature.AllowISO8601DateFormat);
        } catch (Exception e) {
            logger.error("parse json body="+targetJson+" error!", e);
        }

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        String value = null;
        for (Map<String, String> map : list) {
            value = map.get(key);
            if (value != null){ break;}
        }

        if (StringUtils.isBlank(value)) {
            return null;
        }

        return buildObjectFromJsonValue(value, returnType);
    }

    /**
     * 获取某个Key的值，并转化为对象
     *
     * @param targetJson
     * @param key
     * @param returnType
     * @return
     */
    public static <T> T retrieveFromJsonMap(String targetJson, String key, Class<T> returnType){
        if (StringUtils.isBlank(targetJson)){
            return null;
        }

        Map<String, String> map = null;
        try{
            map = JSON.parseObject(targetJson, new TypeReference<Map<String, String>>(){
            }, Feature.AllowISO8601DateFormat);
        } catch (Exception e) {
            logger.error("parse json body="+targetJson+" error!", e);
        }
        if (null == map || map.isEmpty()) {
            return null;
        }

        String value = map.get(key);

        if (StringUtils.isBlank(value)) {
            return null;
        }

        return buildObjectFromJsonValue(value, returnType);
    }

    private static <T> T buildObjectFromJsonValue(String value, Class<T> returnType){
        if (String.class.isAssignableFrom(returnType)){
            return (T)value;
        }

        if (Integer.class.isAssignableFrom(returnType)){
            return (T)Integer.valueOf(value);
        }else if (Long.class.isAssignableFrom(returnType)){
            return (T) Long.valueOf(value);
        }else if (BigDecimal.class.isAssignableFrom(returnType)){
            return (T) new BigDecimal(value);
        }else if (BigInteger.class.isAssignableFrom(returnType)){
            return (T) new BigInteger(value);
        }else if (Number.class.isAssignableFrom(returnType)){
            return (T) Double.valueOf(value);
        }

        if (Serializable.class.isAssignableFrom(returnType)){
            Map<String, String> map = null;
            try {
                map = JSON.parseObject(value, new TypeReference<Map<String, String>>() {
                }, Feature.AllowISO8601DateFormat);
            } catch (Exception e) {
                logger.error("parse json body="+value+" error!", e);
            }

            if (map == null || map.isEmpty()) {
                return null;
            }

            return BeanUtils.toBean(map, returnType);
        }

        return returnType.cast(value);
    }
  
}


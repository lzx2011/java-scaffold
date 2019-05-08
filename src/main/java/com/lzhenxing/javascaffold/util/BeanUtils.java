package com.lzhenxing.javascaffold.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * ClassName: BeanUtils <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2019/1/1
 */
public class BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * ��mapת����Bean��Bean����������map��keyֵ��Ӧʱ�����ִ�Сд������map��key������OMIT_REG������
     *
     * @param <E>
     * @param map
     * @param clazz
     * @return
     */
    public static <E, V extends Serializable> E toBean(Map<String, V> map, Class<E> clazz) {
        // ��������
        E obj = null;
        try {
            obj = clazz.newInstance();
            if (obj == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("clazz[{}] initialize error", clazz);
            return null;
        }

        // ����map��key
        Map<String, V> tempMap = Maps.newHashMap();
        for (Map.Entry<String, V> entry : map.entrySet()) {
            tempMap.put(String.format("set%s", entry.getKey().trim()).replaceAll("_", "").toLowerCase(), entry.getValue());
        }

        // ����ֵװ��
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName().toLowerCase();
            if (methodName.startsWith("set")) {
                Class[] parameterTypes = method.getParameterTypes();
                Object value = tempMap.get(methodName);

                if (value != null && parameterTypes.length == 1) {
                    try {
                        method.invoke(obj, value);
                    } catch (Exception e) {
                        logger.error("filed set error,method��{}.{}.parameterType{};but want set clazz:{}", clazz, method.getName(), parameterTypes[0], value.getClass());
                    }
                }
            }
        }

        return obj;
    }


}

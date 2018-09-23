package com.lzhenxing.javascaffold.cache.redis;

import com.alibaba.fastjson.TypeReference;
import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Lazy(false)
public class RedisFactoryService {

    private static final Logger logger = LoggerFactory.getLogger(RedisFactoryService.class);

    private static RedisService redisService;

    /**
     * @param key
     * @return
     */
    public static String get(String key) {
        return redisService.get(key);
    }

    /**
     * @param key
     * @param value
     * @param liveTime 单位：秒，为0时表示不设置超时
     */
    public static void set(String key, String value, int liveTime) {
        redisService.set(key, value, liveTime);
    }

    /**
     * @param key
     * @param obj
     * @param liveTime 单位：秒，为0时表示不设置超时
     * @return
     */
    public static boolean set(String key, Object obj, int liveTime) {
        boolean result = false;
        try {
            String jsonValue = FastJsonUtil.bean2Json(obj);
            redisService.set(key, jsonValue, liveTime);
            result = true;
        } catch (Exception e) {
            logger.error("[redis] set key:{} from object:{} occur Error!", key, obj, e);
        }
        return result;
    }

    /**
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> clazz) {
        String jsonValue = redisService.get(key);
        Object obj = null;
        if (StringUtils.isNotEmpty(jsonValue)) {
            try {
                obj = FastJsonUtil.json2Bean(jsonValue, clazz);
            } catch (Exception e) {
                logger.error("[redis] get key:{} to object:{} occur Error!", key, jsonValue, e);
            }
        }
        return (T) obj;
    }

    /**
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> List<T> getList(String key, TypeReference<List<T>> type) {
        String jsonValue = redisService.get(key);
        Object obj = null;
        if (StringUtils.isNotEmpty(jsonValue)) {
            try {
                obj = FastJsonUtil.json2Bean(jsonValue, type);
            } catch (Exception e) {
                logger.error("[redis] get key:{} to object:{} occur Error!", key, jsonValue, e);
            }
        }
        return (List<T>) obj;
    }

    /**
     * @param key
     * @param type
     * @param <C>
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <C, T> Map<C, T> getMap(String key, TypeReference<Map<C, T>> type) {
        String jsonValue = redisService.get(key);
        Object obj = null;
        if (StringUtils.isNotEmpty(jsonValue)) {
            try {
                obj = FastJsonUtil.json2Bean(jsonValue, type);
            } catch (Exception e) {
                logger.error("[redis] get key:{} to object:{} occur Error!", key, jsonValue, e);
            }
        }
        return (Map<C, T>) obj;
    }

    /**
     * @param key
     * @param hashKey
     * @param obj
     * @return
     */
    public static boolean putHash(String key, Object hashKey, Object obj) {
        boolean result = false;
        try {
            redisService.putToHash(key, hashKey, obj);
            result = true;
        } catch (Exception e) {
            logger.error("[redis] put hash element key:{} to hashKey:{} object:{} occur Error!", key, hashKey, obj, e);
        }
        return result;
    }

    /**
     * @param key
     * @param hashKey
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getHash(String key, Object hashKey) {
        try {
            return (T) redisService.getFromHash(key, hashKey);
        } catch (Exception e) {
            logger.error("[redis] get hash element key:{} to hashKey:{} occur Error!", key, hashKey, e);
        }
        return null;
    }

    /**
     * @param key
     * @return
     */
    public static long delete(String... keys) {
        try {
            return redisService.del(keys);
        } catch (Exception e) {
            logger.error("[redis] del key:{} occur Error!", keys, e);
        }
        return 0;
    }

    public static long delete(List<String> keys) {
        try {
            return redisService.del(keys);
        } catch (Exception e) {
            logger.error("[redis] del key:{} occur Error!", keys, e);
        }
        return 0;
    }

    /**
     * 将一个值 value插入到列表 key 的表尾(最右边)。如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * <p/>
     * 返回值： 执行 RPUSH 操作后，列表的长度。
     *
     * @param key
     * @param value
     * @return
     * @since JDK 1.6
     */
    public static long rPush(String key, String value) {
        return redisService.rPush(key, value);
    }

    /**
     * 将一个值 value插入到列表 key 的表头(最左边)。如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * <p/>
     * 返回值： 执行 LPUSH 操作后，列表的长度。
     *
     * @param key
     * @param value
     * @return
     * @since JDK 1.6
     */
    public static long lPush(String key, String value) {
        return redisService.lPush(key, value);
    }

    /**
     * 将一个值 value插入到列表 key 的表尾(最右边)。如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * <p/>
     * 返回值： 执行 RPUSH 操作后，列表的长度。
     *
     * @param key
     * @param obj
     * @return
     * @since JDK 1.6
     */
    public static long rPush(String key, Object obj) {
        String jsonValue = FastJsonUtil.bean2Json(obj);
        return redisService.rPush(key, jsonValue);
    }

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param key
     * @return
     * @since JDK 1.6
     */
    public static String lPop(String key) {
        return redisService.lPop(key);
    }

    /**
     * 移除并返回列表 key 的尾元素。
     *
     * @param key
     * @return
     * @since JDK 1.6
     */
    public static String rPop(String key) {
        return redisService.rPop(key);
    }

    /**
     * 移除并返回列表 key 的头元素。
     *
     * @param key
     * @param clazz
     * @return
     * @since JDK 1.6
     */
    public static <T> T lPop(String key, Class<T> clazz) {
        String jsonValue = redisService.lPop(key);
        T obj = null;
        if (StringUtils.isNotEmpty(jsonValue)) {
            try {
                obj = FastJsonUtil.json2Bean(jsonValue, clazz);
            } catch (Exception e) {
                logger.error("[redis] lPop key:{} to jsonValue:{} occur Error!", key, jsonValue, e);
            }
        }
        return obj;
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * <p/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
     * 表示列表的倒数第二个元素，以此类推。 stop 下标也在 LRANGE 命令的取值范围之内(闭区间)。超出范围的下标值不会引起错误。
     * <p/>
     * 返回值: 一个列表，包含指定区间内的元素。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lRange(String key, long start, long end) {
        return redisService.lRange(key, start, end);
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * <p/>
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
     * 表示列表的倒数第二个元素，以此类推。 stop 下标也在 LRANGE 命令的取值范围之内(闭区间)。超出范围的下标值不会引起错误。
     * <p/>
     * 返回值: 一个列表，包含指定区间内的元素。
     *
     * @param key
     * @param start
     * @param end
     * @param clazz
     * @return
     */
    public static <T> List<T> lRange(String key, long start, long end, Class<T> clazz) {
        List<String> jsonValueList = redisService.lRange(key, start, end);
        List<T> objList = new ArrayList<T>();
        if (jsonValueList != null && !jsonValueList.isEmpty()) {
            for (String jsonValue : jsonValueList) {
                T obj = null;
                if (StringUtils.isNotEmpty(jsonValue)) {
                    try {
                        obj = FastJsonUtil.json2Bean(jsonValue, clazz);
                    } catch (Exception e) {
                        logger.error("[redis] lRange key:{} to jsonValue:{} occur Error!", key, jsonValue, e);
                    }
                }
                if (obj != null) {
                    objList.add(obj);
                }
            }
        }
        return objList;
    }

    /**
     * 
     * lLen:返回列表长度. <br/>
     * 
     * @param key
     * @return
     */
    public static long lLen(String key) {
        return redisService.lLen(key);
    }

    /**
     * 
     * lTrim:Trim list at key to elements between begin and end. <br/>
     * 
     * @param key
     * @param begin
     * @param end
     */
    public static void lTrim(String key, long begin, long end) {
        redisService.lTrim(key, begin, end);
    }

    /**
     * 
     * incr:Increment value of key by 1. <br/>
     * 
     * @param key
     * @param liveTime
     * @return
     */
    public static long incr(String key, long liveTime) {
        return redisService.incr(key, liveTime);
    }

    public static boolean isExist(String key) {
        return redisService.exists(key);
    }
    
    public static String ping() {
        return redisService.ping();
    }

    /**
     * @param value
     */
    @Autowired
    public void setRedisService(RedisService value) {
        RedisFactoryService.redisService = value;
    }

}

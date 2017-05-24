package com.lzhenxing.javascaffold.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * ClassName: RedisServiceImpl <br/>
 * date: 2014年12月12日 下午2:05:17 <br/>
 * 
 * @version
 * @since JDK 1.6
 */
@Component
class RedisServiceImpl implements RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    private static final String redisCode = "utf-8";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private RedisServiceImpl() {
    }

    /**
     * del
     * @param keys
     * @return
     *
     */
    public long del(final String... keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection){
                long result = 0;
                try {
                    for (String key: keys) {
                        result += connection.del(key.getBytes(redisCode));
                    }
                }catch (UnsupportedEncodingException e){
                	logger.error("[redis] del keys occur error",e);
                }
                return result;
            }

        });
    }

    /**
     * del
     * @param keys
     * @return
     *
     */
    public long del(final List<String> keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection){
                long result = 0;
                try {
                    for (String key: keys) {
                        result += connection.del(key.getBytes(redisCode));
                    }
                }catch (UnsupportedEncodingException e){
                    logger.error("[redis] del keys occur error",e);
                }
                return result;
            }

        });
    }
    
    /**
     * set
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection){
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * set
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, long liveTime) {
        try {
            this.set(key.getBytes(redisCode), value.getBytes(redisCode), liveTime);
        }catch (UnsupportedEncodingException e){
        	logger.error("[redis] set key:{} occur error",key,e);
        }
    }

    /**
     * set
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * set
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * get
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            /**
             * @param connection
             * @return
             */
            public String doInRedis(RedisConnection connection){
                try {
                    byte[] result = connection.get(key.getBytes(redisCode));
                    if (result != null) {
                        return new String(result, redisCode);
                    }
                } catch (UnsupportedEncodingException e) {
                	logger.error("[redis] get key:{} occur error",key,e);
                }
                return null;
            }
        });
    }

    /**
     * get
     * @param key
     * @return
     */
    public byte[] get(final byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            /**
             * @param connection
             * @return
             */
            public byte[] doInRedis(RedisConnection connection){
                return connection.get(key);
            }
        });
    }

    /**
     * keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    /**
     * exists
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            /**
             * @param connection
             * @return
             */
            public Boolean doInRedis(RedisConnection connection) {
                try {
                    return connection.exists(key.getBytes(redisCode));
                }catch (UnsupportedEncodingException e){
                	logger.error("[redis] exists key:{} occur error",key,e);
                }
                return false;
            }
        });
    }

    /**
     * flushDB
     * @return
     */
    public String flushDB() {
        return redisTemplate.execute(new RedisCallback<String>() {
            /**
             * @param connection
             * @return
             */
            public String doInRedis(RedisConnection connection){
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * dbSize
     * @return
     */
    public long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection){
                return connection.dbSize();
            }
        });
    }

    /**
     * ping
     * @return
     */
    public String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            /**
             * @param connection
             * @return
             */
            public String doInRedis(RedisConnection connection){

                return connection.ping();
            }
        });
    }
    /**
     * putToHash
     * @param key
     * @param hashKey
     * @param obj
     */
    public void putToHash(String key, Object hashKey, Object obj) {
        redisTemplate.opsForHash().put(key, hashKey, obj);
    }
    /**
     * deleteFromHash
     * @param key
     * @param hashKey
     */
    public void deleteFromHash(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }
    /**
     * getFromHash
     * @param key
     * @param hashKey
     * @return
     */
    public Object getFromHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 将一个值 value插入到列表 key 的表尾(最右边)。如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * 返回值： 执行 RPUSH 操作后，列表的长度。
     * 
     * @param key
     * @param value
     */
    public long rPush(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection) {
                try {
                    return connection.rPush(key.getBytes(redisCode), value.getBytes(redisCode));
                }catch (UnsupportedEncodingException e){
                	logger.error("[redis] rPush key:{} occur error",key,e);
                }
                return 0L;
            }
        });
    }

    /**
     * 将一个值 value插入到列表 key 的表头(最左边)。如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * 返回值： 执行 LPUSH 操作后，列表的长度。
     * 
     * @param key
     * @param value
     */
    public long lPush(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            /**
             * @param connection
             * @return
             */
            public Long doInRedis(RedisConnection connection) {
                try {
                    return connection.lPush(key.getBytes(redisCode), value.getBytes(redisCode));
                }catch (UnsupportedEncodingException e){
                    logger.error("[redis] lPush key:{} occur error",key,e);
                }
                return 0L;
            }
        });
    }
    
    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param key
     * @return
     */
    public String lPop(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            /**
             * @param connection
             * @return
             */
            public String doInRedis(RedisConnection connection) {
                try {
                    byte[] result = connection.lPop(key.getBytes(redisCode));
                    if (result != null && result.length > 0) {
                        return new String(result, redisCode);
                    }
                } catch (UnsupportedEncodingException e) {
                	logger.error("[redis] lPop key:{} occur error",key,e);
                }
                return null;
            }
        });
    }

    /**
     * 移除并返回列表 key 的尾元素。
     * 
     * @param key
     * @return
     */
    public String rPop(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            /**
             * @param connection
             * @return
             */
            public String doInRedis(RedisConnection connection) {
                try {
                    byte[] result = connection.rPop(key.getBytes(redisCode));
                    if (result != null && result.length > 0) {
                        return new String(result, redisCode);
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("[redis] rPop key:{} occur error",key,e);
                }
                return null;
            }
        });
    }
    
    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2
     * 表示列表的倒数第二个元素，以此类推。 stop 下标也在 LRANGE 命令的取值范围之内(闭区间)。超出范围的下标值不会引起错误。
     * 
     * 返回值: 一个列表，包含指定区间内的元素。
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lRange(final String key, final long start, final long end) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            /**
             * @param connection
             * @return
             */
            public List<String> doInRedis(RedisConnection connection) {
                try {
                    List<byte[]> resultList = connection.lRange(key.getBytes(redisCode), start, end);
                    if (resultList != null && !resultList.isEmpty()) {
                        List<String> resultStrList = new ArrayList<String>();
                        for (byte[] result : resultList) {
                            resultStrList.add(new String(result, redisCode));
                        }
                        return resultStrList;
                    }
                } catch (UnsupportedEncodingException e) {
                	logger.error("[redis] lRange key:{} occur error", key, e);
                }
                return Collections.emptyList();
            }
        });
    }
    
    public long lLen(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                try {
                    Long result = connection.lLen(key.getBytes(redisCode));
                    if (result != null) {
                        return result.longValue();
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("[redis] lLen key:{} occur error",key,e);
                }
                return 0L;
            }
        });
    }

    public void lTrim(final String key, final long begin, final long end) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                try {
                    connection.lTrim(key.getBytes(redisCode), begin, end);
                } catch (UnsupportedEncodingException e) {
                    logger.error("[redis] lTrim key:{} occur error",key,e);
                }
                return 0L;
            }
        });
    }
    
    /**
     * 
     * Increment value of key by 1.
     */
    public Long incr(final String key, final long liveTime) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) {
                try {
                    byte[] keys = key.getBytes(redisCode);
                    Long result = connection.incr(keys);
                    if (liveTime > 0) {
                        connection.expire(keys, liveTime);
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    logger.error("[redis] incr key:{} occur error",key,e);
                }
                return 0L;
            }
        });
    }
    
}

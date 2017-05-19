package com.lzhenxing.javascaffold.cache.redis;

import java.util.List;
import java.util.Set;

/**
 * ClassName: RedisService <br/>
 * date: 2014年12月12日 下午2:05:09 <br/>
 * 
 * @version
 * @since JDK 1.6
 */
interface RedisService {
    /**
     * 通过多个key删除
     * del
     * @param keys
     * @return
     */
    long del(String... keys);
    
    /**
     * 通过key列表删除
     * del
     * @param keys
     * @return
     */
    long del(List<String> keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 添加key value
     * 
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     * 
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     * 
     * @param key
     * @return
     */
    String get(String key);
    /**
     * 获取redis value
     *
     * @param key
     * @return
     */
    byte[] get(final byte[] key);

    /**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     * 
     * @return
     */
    String flushDB();

    /**
     * 查看redis里有多少数据
     * @return
     */
    long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    String ping();
    /**
     * @param key
     * @param hashKey
     * @param obj
     */
    void putToHash(String key, Object hashKey, Object obj);
    /**
     * @param key
     * @param hashKey
     */
    void deleteFromHash(String key, Object hashKey);
    /**
     * @param key
     * @param hashKey
     * @return
     */
    Object getFromHash(String key, Object hashKey);

    /**
     * 将一个值 value插入到列表 key 的表尾(最右边)。如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * 返回值： 执行 RPUSH 操作后，列表的长度。
     * 
     * @param key
     * @param value
     * @return
     * @since JDK 1.6
     */
    long rPush(String key, String value);
    
    /**
     * 将一个值 value插入到列表 key 的表头(最左边)。如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误。
     * 
     * 返回值： 执行 LPUSH 操作后，列表的长度。
     * 
     * @param key
     * @param value
     * @return
     * @since JDK 1.6
     */
    long lPush(String key, String value);

    /**
     * 移除并返回列表 key 的头元素。
     * 
     * @param key
     * @return
     * @since JDK 1.6
     */
    String lPop(String key);
    
    /**
     * 移除并返回列表 key 的尾元素。
     * 
     * @param key
     * @return
     * @since JDK 1.6
     */
    String rPop(String key);

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
    List<String> lRange(String key, long start, long end);
    
    /**
     * 
     * lLen:返回列表长度. <br/>  
     *  
     * @param key
     * @return
     */
    long lLen(String key);
    
    /**
     * 
     * lTrim:Trim list at key to elements between begin and end. <br/>  
     *  
     * @param key
     * @param begin
     * @param end
     */
    void lTrim(String key, long begin, long end);
    
    /**
     * 
     * incr:Increment value of key by 1. <br/>  
     *  
     * @param key
     * @param liveTime
     * @return
     */
    Long incr(String key, long liveTime);
}

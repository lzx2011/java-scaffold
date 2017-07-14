package com.lzhenxing.javascaffold.cache.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName: RedisTest <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/24
 */
public class RedisTest {


    public static void main(String[] args){

        ApplicationContext app = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        //RedisService redisService = (RedisService) app.getBean("redisService");

        System.out.println(RedisFactoryService.get("foo"));

        String ellipsisValue = "我买了才戴，可是戴一天就坏了……请问这个事、我怎么联系，怎么给我处理啊";
        String ellipsisKey = "ellipsis";
        String slashKey = "slash";
        String slashValue = "dadaf\"daf\\dad";
        RedisFactoryService.set(ellipsisKey, ellipsisValue, 3600);
        System.out.print(RedisFactoryService.get(ellipsisKey));
        //System.in.close();

    }

}

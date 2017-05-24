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

    }

}

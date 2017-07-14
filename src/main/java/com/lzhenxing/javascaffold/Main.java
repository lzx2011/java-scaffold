package com.lzhenxing.javascaffold;

import com.lzhenxing.javascaffold.service.ServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * ClassName: Main <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/7/11
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){

        try{
            AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

            ServiceTest serviceTest = (ServiceTest) ctx.getBean("serviceTest");
            serviceTest.test();

            //ApplicationContextCache.getInstance().setContext(ctx);
            //ctx.registerShutdownHook();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}

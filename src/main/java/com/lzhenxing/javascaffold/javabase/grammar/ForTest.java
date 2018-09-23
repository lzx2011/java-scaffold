package com.lzhenxing.javascaffold.javabase.grammar;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 *
 * ClassName: ForTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/4/22
 */
public class ForTest {

    @Test
    public void retryTest(){
        int retry = 3;
        String result = "";
        try {
            while(StringUtils.isBlank(result) && retry > 0){
                System.out.println("retry:" + retry);
                Thread.sleep(500);
                retry--;
            }

        } catch (Exception e){

        }
    }

    @Test
    public void circulateTest(){
        for(int i = 0; i < 0; i++){
            System.out.println("test");
        }
    }
}

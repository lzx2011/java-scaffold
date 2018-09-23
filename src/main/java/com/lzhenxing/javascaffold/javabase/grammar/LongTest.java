package com.lzhenxing.javascaffold.javabase.grammar;

import org.junit.Test;

/**
 *
 * ClassName: LongTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/4/19
 */
public class LongTest {

    @Test
    public void equalTest(){
        //Long i = 8L;
        //Long j = 8L;
        Long i = 5678L;
        Long j = 5678L;
        //小心这里的坑
        System.out.println(i == j);
        System.out.println(i.equals(j));
    }
}

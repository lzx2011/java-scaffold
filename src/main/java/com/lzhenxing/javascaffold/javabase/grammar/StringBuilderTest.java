package com.lzhenxing.javascaffold.javabase.grammar;

import org.junit.Test;

/**
 * ClassName: StringBuilderTest <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/26
 */
public class StringBuilderTest {

    @Test
    public void appendNull(){
        StringBuilder builder = new StringBuilder("111");
        String str = null;
        builder.append(str);
        System.out.println(builder.toString());  //输出 111null
    }
}

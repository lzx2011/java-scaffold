package com.lzhenxing.javascaffold.javabase.grammar;

import org.junit.Test;

/**
 *
 * ClassName: DoubleTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/5/16
 */
public class DoubleTest {

    @Test
    public void doubleEqual(){
        Double test = 0d;
        //if(0D.equals(test)){
        //    System.out.println("false");
        //}
        if(0 == test){
            System.out.println("true1");
        }

        if(test.equals(0.0)){
            System.out.println("true2");
        }
    }

    @Test
    public void doubleCalculate(){
        double a = 237160;
        double b = 0.8792;
        System.out.println(a*b/100);
    }
}

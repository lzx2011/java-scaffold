package com.lzhenxing.javascaffold.util;

import java.math.BigDecimal;

/**
 *
 * ClassName: UtilsTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/1
 */
public class UtilsTest {

    public static void mathPractice(){
        String interceptRatio = "0.8";

        BigDecimal ratioDecimal = new BigDecimal(interceptRatio).subtract(new BigDecimal(1)).abs();
        BigDecimal settle = new BigDecimal("70.8");
        BigDecimal order = new BigDecimal("78.8");
        System.out.println(settle.subtract(order).abs().divide(order, 4, BigDecimal.ROUND_HALF_UP).toString());
        System.out.println(ratioDecimal.toString());
        if(settle.subtract(order).abs().divide(order, 4, BigDecimal.ROUND_HALF_UP).compareTo(ratioDecimal) > 0){
            System.out.println("true");
        }
    }

    public static void div(){
        String v1 = "1";
        String v2 = "6";
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }


    public static void main(String[] args){
        mathPractice();
    }
}

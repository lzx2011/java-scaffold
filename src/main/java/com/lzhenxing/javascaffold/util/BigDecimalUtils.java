package com.lzhenxing.javascaffold.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * ClassName: BigDecimalUtils <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2017/10/11
 */
public class BigDecimalUtils {

    //千位分隔符 方便查看金额具体大小
    public static final String BIG_NUM_FMT_COMMA = "#,###,###,###,###,###,##0.00";
    //不带千位分隔符
    public static final String BIG_NUM_FMT = "##################0.00";
    public static final String BIG_NUM_HUNDRED = "100";
    //保留两位小数
    public static final int BIG_NUM_SCALE = 2;


    private BigDecimalUtils(){

    }
    /**
     * 高精度加法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2);
    }

    /**
     * 高精度减法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
    }

    /**
     * 高精度乘法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(String v1,String v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2);
    }

    /**
     * 高精度除法
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static BigDecimal div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留小数位
     * @param v
     * @param scale
     * @return
     */
    public static BigDecimal round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 分转换成元
     * @param v
     * @return
     */
    public static BigDecimal penny2dollar(String v){
        //保留两位小数
        BigDecimal s = div(v,"100",2);
        return s;
    }

    /**
     * 格式化金额
     * 千位分隔符 方便查看金额具体大小 BIG_NUM_FMT = "#,###,###,###,###,###,##0.00"
     * 精确两位小数 .99 -> 0.99
     *             1111111.985 -> 1,111,111.99
     * @param v
     * @return
     */
    public static String formatNumber(String v){
        return formatNumber(v,BIG_NUM_FMT_COMMA);
    }

    /**
     * 格式化金额
     * @param v
     * @param pattern BigNum类中的常量 BIG_NUM_FMT_COMMA,BIG_NUM_FMT
     * @return
     */
    public static String formatNumber(String v,String pattern) {
        return new DecimalFormat(pattern).format(new BigDecimal(v));
    }

    public static void main(String[] args){
        String v1 = "1";
        String v2 = "6";
        System.out.println(div(v1, v2, 2).toString());


    }


}

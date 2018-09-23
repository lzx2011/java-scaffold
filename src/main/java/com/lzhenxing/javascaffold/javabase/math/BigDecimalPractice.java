package com.lzhenxing.javascaffold.javabase.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * ClassName: BigDecimalPractice <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/19
 */
public class BigDecimalPractice {

    public static void div(){

        BigDecimal averageSettlePrice = new BigDecimal(1);
        BigDecimal averageOrderPrice = new BigDecimal(10);

        if (averageSettlePrice.compareTo(averageOrderPrice) < 0 && averageOrderPrice.compareTo(BigDecimal.ZERO) != 0) {
            MathContext mathContext = new MathContext(1, RoundingMode.DOWN);
            String averagePriceKV = averageSettlePrice.divide(averageOrderPrice, mathContext).toString();
            System.out.println(averagePriceKV);
        }
    }

    public static void main(String[] args) {
        div();
    }
}

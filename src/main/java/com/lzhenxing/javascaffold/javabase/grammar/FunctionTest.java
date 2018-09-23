package com.lzhenxing.javascaffold.javabase.grammar;

/**
 *
 * ClassName: FunctionTest <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/11
 */
public class FunctionTest {

    public static void transferValue(String msg, StringBuilder stringBuilder){

        String temp = "test";
        msg = temp;

        stringBuilder.append("2324");

    }

    public static void main(String[] args) {
        String message = "1";
        StringBuilder stringBuilder = new StringBuilder("1");
        transferValue(message, stringBuilder);
        System.out.println(message);
        System.out.println(stringBuilder.toString());

    }

}

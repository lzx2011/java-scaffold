package com.lzhenxing.javascaffold.javabase.monitor.cpu;

/**
 * ClassName: CpuTest <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/8/25
 */
public class CpuTest {

    public static void highCpu(){

        while(true){
            System.out.println("high cpu");
        }
    }

    public static void main(String[] args){

        highCpu();

    }
}

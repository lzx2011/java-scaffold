package com.lzhenxing.javascaffold.javabase.exception;

/**
 * Created by gary on 2017/3/2.
 */
public class DeadCycle {

    public static void deadCycleTest(){
        System.out.println("start");
        while(true){
            System.out.println(1);
            DeadCycle deadCycle = new DeadCycle();
        }
        //System.out.println("end");
    }


    public static void main(String[] args){
        deadCycleTest();
    }

}

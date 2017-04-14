package com.lzhenxing.javascaffold.javabase.math;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by gary on 16/7/14.
 */
public class Calculator {

    public static void rounding(){
        System.out.println((int) Math.ceil((double)3/2));
    }

    public static void index(){
        long result=1L;
        System.out.println(Long.MAX_VALUE);
        System.out.println(Long.MIN_VALUE);
        for(int i=0; i<63; i++){
            result *= 2;
        }
        System.out.println((result-1)*2);


    }

//    public static rightMove

    public static byte[] longToBytes(long n) {
        byte[] b = new byte[8];
        b[7] = (byte) (n & 0xff);
        b[6] = (byte) (n >> 8  & 0xff);
        b[5] = (byte) (n >> 16 & 0xff);
        b[4] = (byte) (n >> 24 & 0xff);
        b[3] = (byte) (n >> 32 & 0xff);
        b[2] = (byte) (n >> 40 & 0xff);
        b[1] = (byte) (n >> 48 & 0xff);
        b[0] = (byte) (n >> 56 & 0xff);
        return b;
    }

    public static void whichDb(){
        long num = 79094468458309082L;
        System.out.println(num >> 48 & 0xff);
    }

    public static void test(){
        System.out.println(1<<30);
    }

    public static void testHashMap(){

        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf" + i).start();
                }
            }
        }, "ftf");
        t.start();
        try{
            t.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args){
//        index();
//        test();
//        testHashMap();
//        whichDb();
        rounding();


    }
}

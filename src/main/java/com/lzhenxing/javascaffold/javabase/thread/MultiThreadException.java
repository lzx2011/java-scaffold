package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: MultiThreadException <br/>
 * Function: 多线程中线程中有异常如何处理,不通过的提交方式对异常的处理不太一样<br/>
 *
 * @author gary.liu
 * @date 2017/6/27
 */
public class MultiThreadException {

    static class MyThread implements Runnable{

        public void run(){

            System.out.println(Thread.currentThread().getName() + " start");
            //throw new RuntimeException("my test exception");
            double num = 1/0;

            System.out.println(Thread.currentThread().getName() + " finish");

        }

    }

    public static void threadException(){

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(int i = 0; i < 6; i++){
            //execute会抛出和打印线程中的异常
            //executorService.execute(new MyThread());
            //submit不会打印异常,把异常吞了
            executorService.submit(new MyThread());
        }

    }

    public static void main(String[] args){

        threadException();

    }
}

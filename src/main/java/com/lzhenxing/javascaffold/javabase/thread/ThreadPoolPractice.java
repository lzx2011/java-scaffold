package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by gary on 2017/3/1.
 */
public class ThreadPoolPractice {

    public static void fixThreadTest(){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void cronThread(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        //for(int i = 0; i < 1; i++){
            //scheduledThreadPool.schedule(new ThreadPractice(), 5, TimeUnit.SECONDS);
            scheduledThreadPool.scheduleWithFixedDelay(new ThreadPractice(), 0, 3, TimeUnit.SECONDS);
        //}
        //scheduledThreadPool.shutdown();
    }


    public static void main(String[] args){
        //fixThreadTest();
        ThreadPoolPractice practice = new ThreadPoolPractice();
        practice.cronThread();
    }


    class ThreadPractice implements Runnable{

        public void run(){
            System.out.println("start work");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

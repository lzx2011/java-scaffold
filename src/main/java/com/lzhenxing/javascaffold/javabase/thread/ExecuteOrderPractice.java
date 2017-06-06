package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gary on 2017/3/8.
 *
 * 使用线程池实现其他线程执行完后,再执行其他工作
 */
public class ExecuteOrderPractice {

    public void orderPractice(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for(int i = 0; i < 5; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + " do something");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }

        executorService.shutdown();

        while(true){
            if(executorService.isTerminated()){
                System.out.println("Finally do something ");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new ExecuteOrderPractice().orderPractice();

    }
}

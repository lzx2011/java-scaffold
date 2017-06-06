package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: VolatilePractice <br/>
 * Function: <br/>
 *
 * @author Gary.liu
 * @date 2017/3/18
 */
public class VolatilePractice {

    private volatile int i = 0;

    public int get(){
        return i;  ////单个volatile变量的读与写具有原子性
    }

    public void set(int n){
        this.i = n;
    }

    public synchronized void getAndIncrement(){
        i++;  ////复合（多个）volatile变量的读/写不具有原子性
    }

    static class VolatileAtomicity implements Runnable{

        private VolatilePractice volatilePractice;

        public VolatileAtomicity(VolatilePractice volatilePractice){
            this.volatilePractice = volatilePractice;
        }

        public void run(){
            volatilePractice.getAndIncrement();
        }
    }

    public static void main(String[] args){

        VolatilePractice volatilePractice = new VolatilePractice();

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for(int i = 0; i < 10000; i++){
            executorService.execute(new VolatileAtomicity(volatilePractice));
        }

        executorService.shutdown();
        //判断是否所有线程执行完成
        while(executorService.isTerminated()){

            System.out.println(volatilePractice.get());
            break;
        }

    }
}



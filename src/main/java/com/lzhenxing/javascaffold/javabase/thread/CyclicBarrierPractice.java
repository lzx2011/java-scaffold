package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName: CyclicBarrierPractice <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/2
 */
public class CyclicBarrierPractice {

    static class Worker implements Runnable{
        private String name;
        private CyclicBarrier cyclicBarrier;
        public Worker(String name, CyclicBarrier cyclicBarrier){
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void run(){
            System.out.println(name + " is working");
            try {
                Thread.sleep(1000);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    static class Boss implements Runnable{
        private String name;

        public Boss(String name){
            this.name = name;
        }

        public void run(){
            System.out.println(name + " checking work");

        }
    }

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Boss("boss"));
        for(int i=0; i<3; i++){
            new Thread(new Worker("worker"+i, cyclicBarrier)).start();
        }
    }
}

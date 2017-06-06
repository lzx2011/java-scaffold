package com.lzhenxing.javascaffold.javabase.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName: CyclicBarrierPractice <br/>
 * Function: 三个工人干完后,老板检查工作<br/>
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
                Thread.sleep(2000);

                //到达屏障出（同步点）
                cyclicBarrier.await();

                //线程都到了后继续向下执行,也可以不要下面代码,什么都不做了
                System.out.println(name + " do other things");
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
            System.out.println(name + " checks work");

        }
    }

    public static void main(String[] args){

        //其他线程都达到屏障后,再执行 boss 线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Boss("boss"));

        for(int i=0; i<3; i++){
            new Thread(new Worker("worker"+i, cyclicBarrier)).start();
        }
    }
}

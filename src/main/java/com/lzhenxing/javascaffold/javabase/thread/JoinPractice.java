package com.lzhenxing.javascaffold.javabase.thread;

/**
 * ClassName: JoinPractice <br/>
 * Function: join 阻塞调用此方法的线程,3个worker线程执行后,再执行 Boss 线程<br/>
 *
 * @author gary.liu
 * @date 2017/6/5
 */
public class JoinPractice {

   static class Worker implements Runnable {

        private String name;

        public Worker(String name){
            this.name = name;
        }

        @Override
        public void run(){
            System.out.println(name + " is working");

        }
    }

    static class Boss implements Runnable{

        private String name;

        public Boss(String name){
            this.name = name;
        }

        @Override
        public void run(){
            System.out.println("boss checks work");
        }
    }

    public static void main(String[] args){

        Worker worker1 = new Worker("worker1");
        Worker worker2 = new Worker("worker2");
        Worker worker3 = new Worker("worker3");
        Boss boss = new Boss("boss");

        Thread thread1 = new Thread(worker1);
        Thread thread2 = new Thread(worker2);
        Thread thread3 = new Thread(worker3);
        Thread thread4 = new Thread(boss);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();

            //thread1.wait(1000);
            //thread2.wait(1000);
            //thread3.wait(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread4.start();
    }

}

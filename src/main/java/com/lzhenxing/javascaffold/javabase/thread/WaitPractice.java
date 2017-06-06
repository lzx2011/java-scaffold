package com.lzhenxing.javascaffold.javabase.thread;

import javax.lang.model.element.Name;

/**
 * ClassName: WaitPractice <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/6/5
 */
public class WaitPractice {

    //public static Object object = new Object();

    static class Worker implements Runnable {

        private String name;
        private Object object;

        public Worker(String name, Object object){
            this.name = name;
            this.object = object;
        }

        @Override
        public void run(){
            synchronized (object){
                System.out.println(name + " is working");
                if("worker3".equals(name)){
                    object.notifyAll();
                }
            }
        }

    }

    static class Boss implements Runnable {

        private String name;
        private Object object;

        public Boss(String name, Object object){
            this.name = name;
            this.object = object;
        }

        @Override
        public void run(){
            synchronized (object){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " checks work");
            }
        }

    }

    public static void main(String[] args){

        Object object = new Object();

        Thread worker1 = new Thread(new Worker("worker1", object));
        Thread worker2 = new Thread(new Worker("worker2", object));
        Thread worker3 = new Thread(new Worker("worker3", object));
        Thread boss = new Thread(new Boss("boss", object));

        boss.start();
        worker1.start();
        worker2.start();
        worker3.start();

        //while(true){
        //    if(!worker1.isAlive() && !worker2.isAlive() && !worker3.isAlive()){
        //        System.out.println("all over");
        //        object.notifyAll();
        //        break;
        //    }

        //}
    }
}

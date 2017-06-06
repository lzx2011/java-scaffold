package com.lzhenxing.javascaffold.javabase.thread;

/**
 * Created by gary on 2017/3/13.
 *
 * 老板安排工作后通知三个工人开工
 *
 * notify()        -- 唤醒在此对象监视器上等待的单个线程。
 * notifyAll()   -- 唤醒在此对象监视器上等待的所有线程。
 * wait()   -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法”，当前线程被唤醒(进入“就绪状态”)。
 * wait(long timeout)  -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量”，当前线程被唤醒(进入“就绪状态”)。
 *
 * 补充说明一下"执行wait()进入等待状态的线程，有下面4种唤醒方式。"
 *(01) 通过notify()唤醒
 *(02) 通过notifyAll()唤醒
 *(03) 通过interrupt()中断唤醒
 *(04) 如果是通过调用wait(long timeout)进入等待状态的线程，当时间超时的时候，也会被唤醒。
 *
 */
public class WaitAndNotify {

    public void waitPractice() throws InterruptedException{
        Object object = new Object();
        Object object1 = new Object();
        synchronized (object){
            object.wait();
            object.notify();
        }
    }

   static class Worker implements Runnable{

        private String name;
        private Object object;

        public Worker(String name,Object object){
            this.object = object;
            this.name = name;
        }

        public void run(){
            synchronized(object){
                try {
                    //Thread.sleep(2000); //持有 object 锁,可以看出等2s后释放锁,Boss 线程才能运行
                    object.wait();    //wait 不持有锁

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " is working");
            }
        }
    }

   static class Boss implements Runnable{

        private String name;
        private Object object;

        public Boss(String name, Object object){
            this.name = name;
            this.object = object;
        }

        public void run(){
            synchronized(object){
                System.out.println(name + " has arranged the work");
                object.notifyAll();
                //object.notify();  //只会通知一个wait的线程
            }
        }
    }

    public static void main(String[] args) throws Exception{

        //WaitAndNotify waitAndNotify = new WaitAndNotify();
        //waitAndNotify.waitPractice();

        Object object = new Object();

        new Thread(new Worker("work2", object)).start();
        new Thread(new Worker("work1", object)).start();
        new Thread(new Worker("work3", object)).start();

        new Thread(new Boss("boss", object)).start();
    }
}



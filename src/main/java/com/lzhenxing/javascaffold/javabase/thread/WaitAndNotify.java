package com.lzhenxing.javascaffold.javabase.thread;

/**
 * Created by gary on 2017/3/13.
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

    public static void main(String[] args) throws Exception{

        WaitAndNotify waitAndNotify = new WaitAndNotify();
        //waitAndNotify.waitPractice();

        Object object = new Object();

        new Thread(new Worker("work1", object)).start();
        new Thread(new Worker("work2", object)).start();
        new Thread(new Worker("work3", object)).start();

        new Thread(new Boss("boss", object)).start();


    }
}

class Worker implements Runnable{

    private String name;
    private Object object;

    public Worker(String name,Object object){
        this.object = object;
        this.name = name;
    }

    public void run(){
        synchronized(object){
            try {
                object.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " is working");
        }
    }
}

class Boss implements Runnable{

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
            //object.notify();
        }
    }
}

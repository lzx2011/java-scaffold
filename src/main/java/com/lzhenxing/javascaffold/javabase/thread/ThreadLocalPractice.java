package com.lzhenxing.javascaffold.javabase.thread;

/**
 * ClassName: ThreadLocalPractice <br/>
 * Function: <br/>
 *
 * @author Gary.liu
 * @date 2017/3/18
 */
public class ThreadLocalPractice {

    public static void main(String[] args){

        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
    }
}

class MyThread implements Runnable{

    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public void run(){
        threadLocal.set((int)(Math.random()*1000));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
    }
}

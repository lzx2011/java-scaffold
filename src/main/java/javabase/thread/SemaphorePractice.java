package javabase.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by gary on 2017/3/14.
 */
public class SemaphorePractice {

    public void semaphorePrac(){
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < 10; i++){
            executorService.execute(new Runnable(){
                public void run(){
                    try {
                        semaphore.acquire();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + " is working");
                    semaphore.release();

                }
            });
        }
        executorService.shutdown();
    }

    public static void main(String[] args){
        SemaphorePractice semaphorePractice = new SemaphorePractice();
        semaphorePractice.semaphorePrac();
    }

}

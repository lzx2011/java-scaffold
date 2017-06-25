package com.lzhenxing.javascaffold.javabase.lock;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: MyDeadLock <br/>
 * Function: 死锁示例<br/>
 *
 * @author gary.liu
 * @date 2017/6/24
 */
public class MyDeadLock {

	private static Object object1 = new Object();
	private static Object object2 = new Object();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

	static class Worker implements Runnable {

		public void run() {
			synchronized (object1) {

                //try {
                //    countDownLatch.await();
                //} catch (InterruptedException e) {
                //    Thread.currentThread().interrupt();
                //    e.printStackTrace();
                //}

                System.out.println("Worker has object1 lock!");
                /**
                 *
                 * 不睡一会的话,由于两个线程的运行时间可能不是同时的,就会有一个先运行,一个后运行,此时可能不会产生死锁;
                 * 如果线程几乎同时开始运行,则就有可能产生死锁,加这个时间就是让死锁产生的更明显.
                 *
                 * 为了证明上面的说法,使用 CountDownLatch 让两个线程同时运行来看看结果
                 *
                 */
                try {
					Thread.sleep(500);
                } catch (InterruptedException e) {
					e.printStackTrace();
                }

                synchronized (object2) {
                    //由于产生死锁,无法输出
                    System.out.println("Worker has object2 lock!");
                }
			}


		}
	}

	static class Boss implements Runnable {

		public void run() {
			synchronized (object2) {

                //try {
                //    countDownLatch.await();
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}

                System.out.println("Boss has object2 lock!");

                try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

                synchronized (object1) {
                    //由于产生死锁,无法输出
                    System.out.println("Boss has object1 lock!");

                }
			}
		}
	}

	public static void main(String[] args) throws Exception{

		Thread worker = new Thread(new Worker());
		Thread boss = new Thread(new Boss());

        //因为用countDownLatch.await();两个线程被阻塞了
		worker.start();
		boss.start();

        //Thread.sleep(3000); //更清楚的看到 worker,boss 线程被阻塞
        //countDownLatch.countDown();  //之后两个线程才开始同时继续执行,基本每次都会产生死锁

	}

}

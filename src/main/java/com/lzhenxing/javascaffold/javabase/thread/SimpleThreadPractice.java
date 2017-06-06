package com.lzhenxing.javascaffold.javabase.thread;

/**
 * Created by gary on 2017/3/15.
 */
public class SimpleThreadPractice {

	public static void main(String[] args) {
        MyThread mythread = new MyThread();
		mythread.start();

		new Thread(new TestThread()).start();
	}

	private static class Runner implements Runnable {

		private volatile boolean on = true;

		public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(2000);
                    System.out.println("sleeping");
                } catch (InterruptedException e) {

                }
            }
        }

		public void cancel() {
			on = false;
			Thread.currentThread().interrupt(); // 同时调用中断
		}
	}

	static class MyThread extends Thread {

		public void run() {
			System.out.println(Thread.currentThread() + " extends Thread");
		}
	}

	static class TestThread implements Runnable {

		public void run() {
			System.out.println(Thread.currentThread() + " implements Runnable");
		}
	}

}



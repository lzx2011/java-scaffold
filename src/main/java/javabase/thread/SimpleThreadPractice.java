package javabase.thread;

/**
 * Created by gary on 2017/3/15.
 */
public class SimpleThreadPractice {

	public static void main(String[] args) {
		Mythread mythread = new Mythread();
		mythread.start();

		new Thread(new TestThread()).start();
	}

//	private static class Runner implements Runnable {
//		private volatile boolean on = true;
//
//		public void run() {
//            while (on && !Thread.currentThread().isInterrupted()){
//                try {
//                    queue.put(i); //业务阻塞操作
//                } catch (InterruptedException e) {
//                //该代码还被上层调用时，抛出 or 恢复中断状态
//                    throw e;
//                    或 Thread.currentThread().interrupt();
//
//                //该代码不再被上层调用
//                    break;
//                }
//            }
//        }
//
//		public void cancel() {
//			on = false;
//			Thread.currentThread().interrupt(); // 同时调用中断
//		}
//	}
}

class Mythread extends Thread {

	public void run() {
		System.out.println(Thread.currentThread() + " extends Thread");
	}
}

class TestThread implements Runnable {

	public void run() {
		System.out.println(Thread.currentThread() + " implements Runnable");
	}
}

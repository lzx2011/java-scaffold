package javabase.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by gary on 2017/3/7.
 */
public class OrderThreadExecute {

	class Worker implements Runnable {
		private CountDownLatch downLatch;
		private String name;

		public Worker(CountDownLatch downLatch, String name) {
			this.downLatch = downLatch;
			this.name = name;
		}

		@Override
		public void run() {
			this.doWork();
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(10));
			} catch (InterruptedException ie) {
			}
			System.out.println(this.name + "活干完了！");
			this.downLatch.countDown();
		}

		private void doWork() {
			System.out.println(this.name + "正在干活...");
		}

	}

	class Boss implements Runnable {
		private CountDownLatch downLatch;

		public Boss(CountDownLatch downLatch) {
			this.downLatch = downLatch;
		}

		@Override
		public void run() {
			System.out.println("老板正在等所有的工人干完活......");
			try {
				this.downLatch.await();
			} catch (InterruptedException e) {
			}
			System.out.println("工人活都干完了，老板开始检查了！");
		}

	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CountDownLatch latch = new CountDownLatch(3);

		OrderThreadExecute orderThread = new OrderThreadExecute();

		Worker w1 = orderThread.new Worker(latch, "张三");
		Worker w2 = orderThread.new Worker(latch, "李四");
		Worker w3 = orderThread.new Worker(latch, "王二");

		Boss boss = orderThread.new Boss(latch);

		executor.execute(boss);
		executor.execute(w3);
		executor.execute(w2);
		executor.execute(w1);

		executor.shutdown();


		//使用join方法顺序执行
        //OrderThread worker1 = orderThread.new OrderThread("worker1");
        //OrderThread worker2 = orderThread.new OrderThread("worker2");
        //OrderThread boss = orderThread.new OrderThread("boss");
        //
        //Thread thread1 = new Thread(worker1);
        //Thread thread2 = new Thread(worker2);
        //Thread thread3 = new Thread(boss);
        //
        //thread1.start();
        //thread2.start();
        //
        //try {
         //   thread1.join();
         //   thread2.join();
        //} catch (InterruptedException e) {
         //   e.printStackTrace();
        //}
        //
        //thread3.start();

	}

    //class OrderThread implements Runnable{
    //    private String name;
    //
    //    public OrderThread(String name){
    //        this.name = name;
    //    }
    //
    //    public void run(){
    //        System.out.println(name + " is working");
    //    }
    //}

}

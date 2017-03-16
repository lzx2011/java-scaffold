package javabase.thread;

import java.util.concurrent.*;

/**
 * Created by gary on 2017/3/8.
 */
public class ThreadCallablePractice {

    public void callableTest() throws Exception{

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future = executorService.submit(new Task());
        executorService.shutdown();
        System.out.println(future.get());
    }

    public void futureTaskTest() throws Exception{
        FutureTask<String> futureTask = new FutureTask<String>(new Task());
        //new Thread(futureTask).start();
        //或者仍用线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(futureTask);
        executorService.shutdown();
        System.out.println(futureTask.get());
    }

    public static void main(String[] args) throws Exception{
        ThreadCallablePractice practice = new ThreadCallablePractice();
        //practice.callableTest();
        practice.futureTaskTest();

    }

}

class Task implements Callable<String>{

    public String call(){
        System.out.println(Thread.currentThread().getName() + " is working");
        return "callable result";
    }
}

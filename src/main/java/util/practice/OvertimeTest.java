package util.practice;

import java.util.concurrent.*;

/**
 * Created by gary on 16/5/17.
 */
public class OvertimeTest {
    public static void main(String[] args){
        Callable<String> task = new Callable<String>(){

            public String call() throws Exception{
                System.out.println("test!!");
                Thread.sleep(3000);
                return "success";
            }

        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(task);
        try{
            String result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        }catch (InterruptedException e){
            System.out.println("InterruptedException---");
            e.printStackTrace();
        }catch(ExecutionException e){
            System.out.println("ExecutionException---");
            e.printStackTrace();
        }catch(TimeoutException e){
            System.out.println("timeout---");
            e.printStackTrace();

        }finally{
            executorService.shutdown();
        }

    }
}

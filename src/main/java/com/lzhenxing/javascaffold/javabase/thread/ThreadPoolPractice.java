package com.lzhenxing.javascaffold.javabase.thread;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

/**
 *
 * ClassName: ThreadPoolPractice <br/>
 * Function: <br/>
 *
 * @author zhenxing.liu
 * @date 2018/3/27
 */
public class ThreadPoolPractice {

    /**
     * 这两种统计的结果都不对
     */
    //public static volatile int totalAdd = 0;
    //public static int totalAdd = 0;

    /**
     * 实验证明只有用原子类统计多线程才不会出错
     */
    public static volatile AtomicLong totalAdd = new AtomicLong();


    public static void test(){
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

        List<Integer> list = Lists.newArrayList();
        for(int i = 0; i < 5000; i++){
           list.add(i);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(Integer integer : list){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //processOutHotelImageImportByHotel(param);
                        System.out.println(integer);
                        //Thread.sleep(3000);
                    } catch (Exception e) {
                        //log.error("processOutHotelImageImportByHotel error,json="+ JSON.toJSONString(param),e);
                        System.out.println(e);
                    }
                }
            });
        }
        executorService.shutdown();
        System.out.println("main thread");

    }

    public static void test1(){
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

        for(int j = 0; j < 5; j++){
            List<Integer> list = Lists.newArrayList();
            for(int i = 0; i < 5000; i++){
                list.add(i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(Integer integer : list){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //processOutHotelImageImportByHotel(param);
                            System.out.println(integer);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            //log.error("processOutHotelImageImportByHotel error,json="+ JSON.toJSONString(param),e);
                            System.out.println(e);
                        }
                    }
                });
            }
            //不能写在这里会抛异常
            //executorService.shutdown();
        }
        //应该写在最后这里
        executorService.shutdown();
    }

    /**
     * 因为循环中有shutdown，所以会抛出RejectedExecutionException
     * So when shutdown() is called, no more tasks are allowed to be executed.
     */
    public static void test2(){
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

        for(int j = 0; j < 5; j++){
            List<Integer> list = Lists.newArrayList();
            for(int i = 0; i < 5000; i++){
                list.add(i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final CountDownLatch countDownLatch = new CountDownLatch(list.size());
            for(Integer integer : list){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //processOutHotelImageImportByHotel(param);
                            System.out.println(integer);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            //log.error("processOutHotelImageImportByHotel error,json="+ JSON.toJSONString(param),e);
                            System.out.println(e);
                        }finally {
                            countDownLatch.countDown();
                        }
                    }
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //executorService.shutdown();
        }
    }

    public static void test3(){
        String test = null;
        System.out.println(JSON.toJSONString(test));
    }

    @Test
    public void statisticTest(){
        List<Integer> list = Lists.newArrayList();
        for(int i = 0; i < 100000; i++){
            list.add(i);
        }
        if(!CollectionUtils.isEmpty(list)){
            ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
            for(Integer integer : list){
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int addCount;
                            if(integer % 2 == 0){
                               addCount = 1;
                            }else {
                                addCount = 2;
                            }
                            totalAdd.getAndAdd(addCount);
                            //totalAdd += addCount;
                        } catch (Exception e) {
                        }
                    }
                });
            }

            executorService.shutdown();

            while(true){
                if(executorService.isTerminated()){
                    System.out.println(totalAdd);
                    break;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //test();
        //test1();
        test3();
    }
}

package com.lzhenxing.javascaffold.javabase.thread;

import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by gary on 2017/3/17.
 */
public class ProducerConsumerPractice {

    public static void main(String[] args){

        Vector<Integer> vector = new Vector<>(5);
        new Thread(new Producer(vector)).start();
        new Thread(new Consumer(vector)).start();

        //LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>(5);
        //new Thread(new Producer(linkedBlockingDeque)).start();
        //new Thread(new Consumer(linkedBlockingDeque)).start();
    }
}

class Producer1 implements Runnable{

    private LinkedBlockingDeque<Integer> linkedBlockingDeque;

    public Producer1(LinkedBlockingDeque<Integer> linkedBlockingDeque){
        this.linkedBlockingDeque = linkedBlockingDeque;
    }

    public void run(){
        for(int i = 0; i < 10; i++){
            try {
                //Thread.sleep(500);
                linkedBlockingDeque.put(i);
                System.out.println("Producer: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Consumer1 implements Runnable{

    private LinkedBlockingDeque<Integer> linkedBlockingDeque;

    public Consumer1(LinkedBlockingDeque<Integer> linkedBlockingDeque){
        this.linkedBlockingDeque = linkedBlockingDeque;
    }

    public void run(){
        while(true){
            try{
                Thread.sleep(500);
                System.out.println("consumer: " + linkedBlockingDeque.take());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Producer implements Runnable{

    private Vector<Integer> vector;

    public Producer(Vector vector){
        this.vector = vector;
    }

    public void run(){
        for(int i = 0; i < 10; i++){
            while(vector.size() == vector.capacity()){
                synchronized (vector){
                    System.out.println("Queue is full, Producer  is waiting , size: " + vector.size());
                    try {
                        vector.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            synchronized (vector){
                vector.add(i);
                System.out.println("Producer: " + i);
                vector.notifyAll();
            }
        }
    }
}

class Consumer implements Runnable{

    private Vector<Integer> vector;

    public Consumer(Vector vector){
        this.vector = vector;
    }

    public void run(){
        while(true){
            while(vector.isEmpty()){
                synchronized (vector){
                    System.out.println("Queue is empty, Consumer is waiting , size: " + vector.size());
                    try {
                        vector.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            //调慢消费速度
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (vector){
                System.out.println("Consumer: " + vector.remove(0));
                vector.notifyAll();
            }
        }
    }

}

package com.lzhenxing.javascaffold.javabase.collections;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gary on 16/8/14.
 */
public class HashMapTest {

    @Test
    public void test(){
        Map<String, String> map = new HashMap<>();
        map.put("1","2");
        for(Map.Entry<String,String> entry : map.entrySet()){
            System.out.println(entry.getValue());
        }


    }

    public static void putTest(){
        long time1 = System.currentTimeMillis();
        Map<Integer, Integer> map1 = new HashMap<>();
        for(Integer i = 0; i < 100000; i++){
            map1.put(i, i);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("time: " + (time2 - time1));
        //System.out.println("map1: " + map1.size());


        Map<Integer, Integer> map2 = new HashMap<>(100000);
        for(Integer i = 0; i < 100000; i++){
            map2.put(i, i);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("time2: " + (time3 - time2));
        System.out.println("map2: " + map1.size());

    }

    public static void main(String[] args){
        putTest();

    }
}

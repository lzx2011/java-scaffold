package com.lzhenxing.javascaffold.javabase.collections;

import com.google.common.collect.Maps;
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

    @Test
    public void addMap(){
        Map<Long, Long> patch = Maps.newHashMap();
        patch.put(1L,2L);
        patch.put(3L,2L);
        Map<Long, Long> target = Maps.newHashMap();
        patch.put(1L,1L);
        patch.put(2L,2L);
        patch.forEach(target::putIfAbsent);

        //use guava
        // Add everything in map1 not in map2 to map2
        target.putAll(Maps.difference(patch, target).entriesOnlyOnLeft());
        System.out.println(target);
    }

    @Test
    public void mapPrac(){
        Map<String, Object> map = Maps.newHashMap();
        //map.put("code", -1);
        if(map.containsKey("code")){
            System.out.println("true");
        }
    }

    public static void main(String[] args){
        //putTest();
        //Long.parseLong(null);
        System.out.println(Double.parseDouble(""));
    }


}

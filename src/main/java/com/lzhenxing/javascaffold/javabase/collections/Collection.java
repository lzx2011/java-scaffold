package com.lzhenxing.javascaffold.javabase.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gary on 16/6/26.
 */
public class Collection {

    public static void testMap(Map<String ,String> map){
        for(Map.Entry<String,String> entry : map.entrySet()){
            System.out.println(entry.getKey()+"-----"+entry.getValue());
        }

    }

    public static void iteratorMap(Map<String,String> map){
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            System.out.println(key+"----"+value);

        }
    }

    @Test
    public  void traverse(){
        //List<Integer> list = Arrays.asList(1,2);
        //list.add(3);   //java.lang.UnsupportedOperationException
        //System.out.println(list.toString());
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        //list.add(3);
        for (Integer i : list){
            if(2 == i){
                //list.set(i, 4);
                list.remove(i);
            }

            //System.out.println(i);
        }
        System.out.println(list.toString());
    }

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
//        map.put("1","test1");
//        map.put("2","test2");
//        testMap(map);
//        iteratorMap(map);
    }
}

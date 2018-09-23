package com.lzhenxing.javascaffold.javabase.collections;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Test
    public void iteratorList(){
        List<Long> list = Lists.newArrayList();
        list.add(1L);
        list.add(2L);
        list.add(365756733L);
        list.add(4L);
        list.add(5L);
        //Long i = 365756733L;
        System.out.println(list);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Long aLong =  (Long)iterator.next();
            //if("3657567".equals(aLong)){
            //    iterator.remove();
            //}
            if(365756733L == aLong){
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    public static boolean filterSellerIdAndVendor(Long sellerId, String vendor){
        boolean flag = false;
        if(sellerId == null || StringUtils.isBlank(vendor)){
            return flag;
        }
        Long i = 2972472251L;
        if(i.longValue() == sellerId && "intl_expedia".equals(vendor)){
            return true;
        }
        if(2272687825L == sellerId && "intl_agoda".equals(vendor)){
            return true;
        }
        if(2978942880L == sellerId && "intl_tourico".equals(vendor)){
            return true;
        }
        if(2944730784L == sellerId && "intl_hotelbeds".equals(vendor)){
            return true;
        }
        if(3070359393L == sellerId && "intl_gta".equals(vendor)){
            return true;
        }
        if(2697996683L == sellerId && "intl_jtb".equals(vendor)){
            return true;
        }
        return flag;
    }

    @Test
    public void listTest(){
        List<Long> list = Lists.newArrayList();
        System.out.println(list.size());
    }

    @Test
    public void streamFilterTest(){
        Long bizOrderId = 1100169310025215469L;
        Set<Long> testSet = new HashSet<>();
        testSet.add(1100169310025215469L);
        testSet.add(1100169310026215469L);

        System.out.println(testSet);
        Set<Long> relateOrderIds = testSet.stream().filter(x -> !x.equals(bizOrderId))
            .collect(Collectors.toSet());
        System.out.println(relateOrderIds);
        System.out.println(testSet);
    }

    public static void main(String[] args){
        Map<String,String> map = new HashMap<>();
//        map.put("1","test1");
//        map.put("2","test2");
//        testMap(map);
//        iteratorMap(map);

        System.out.println(filterSellerIdAndVendor(2972472251L, "intl_expedia"));
    }
}

package com.lzhenxing.javascaffold.javabase.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SubListPractice <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/5
 */
public class SubListPractice {


    /**
     * 分割list
     * @param list
     * @param total
     * @param pageSize
     */
    public static List<List> subList(List list, int total, int pageSize){
        int page =  (int) Math.ceil((double)total/pageSize);
        //int page =  (int) Math.floor(3.1);
        System.out.println("page:" + page);
        int mod = total%pageSize;
        List<List> list1 = new ArrayList<>();

        if(page < 2){
            System.out.println(list.toString());
            return list;
        }
        for(int i=0; i<page-1;i++){
            List subList = list.subList(i*pageSize, (i+1)*pageSize);
            list1.add(subList);
            System.out.println(subList.toString());
        }
        List lastSub;
        if(mod == 0){
            lastSub = list.subList((page-1)*pageSize, page*pageSize);
        }else{
            lastSub = list.subList((page-1)*pageSize,  total);
        }
        list1.add(lastSub);
        System.out.println("lastSub:" + lastSub.toString());
        System.out.println("list1:" + list1.toString());
        return list1;
    }

    public static void initSub(){
        int total = 22;
        int pageSize = 4;
        List<Long> ids = new ArrayList<>();
        for(int i=0; i<total; i++){
            ids.add((long)(i+1));
        }
        System.out.println(ids.toString());
        List<List> list =  subList(ids, total, pageSize);
    }

    public static void main(String[] args){
        initSub();
    }
}

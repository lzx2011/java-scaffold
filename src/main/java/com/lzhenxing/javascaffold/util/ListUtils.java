package com.lzhenxing.javascaffold.util;

import org.apache.http.util.Asserts;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ListUtils <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/4/14
 */
public class ListUtils {

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }

    /**
     * 分割list
     * @param list
     * @param pageSize
     * @return
     */
    public static <T> List<List<T>> splitList(final List<T> list, int pageSize){
        if(isEmpty(list)){
            return null;
        }
        Asserts.check(pageSize > 0, "pageSize must > 0");

        int total = list.size();
        int page =  (int) Math.ceil((double)total/pageSize);
        int mod = total%pageSize;
        List<List<T>> list1 = new ArrayList<List<T>>();

        if(page < 2){
            list1.add(list);
            return list1;
        }
        for(int i = 0; i < page-1; i++){
            List subList = list.subList(i*pageSize, (i+1)*pageSize);
            list1.add(subList);
        }
        List lastSub;
        if(mod == 0){
            lastSub = list.subList((page-1)*pageSize, page*pageSize);
        }else{
            lastSub = list.subList((page-1)*pageSize,  total);
        }
        list1.add(lastSub);
        return list1;
    }

}

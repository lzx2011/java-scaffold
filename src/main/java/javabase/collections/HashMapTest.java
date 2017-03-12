package javabase.collections;

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

    public static void main(String[] args){

    }
}

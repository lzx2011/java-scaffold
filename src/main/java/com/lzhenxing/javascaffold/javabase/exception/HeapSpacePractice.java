package com.lzhenxing.javascaffold.javabase.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: HeapSpacePractice <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2017/5/29
 */
public class HeapSpacePractice {

    static String  base = "string";

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (int i=0;i< Integer.MAX_VALUE;i++){
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }
}

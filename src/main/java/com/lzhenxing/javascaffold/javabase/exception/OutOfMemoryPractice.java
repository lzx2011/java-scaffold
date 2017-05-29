package com.lzhenxing.javascaffold.javabase.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: OutOfMemoryPractice <br/>
 * Function: <br/>
 *
 * 参考:
 * Java8内存模型—永久代(PermGen)和元空间(Metaspace)
 * http://www.cnblogs.com/paddix/p/5309550.html
 *
 * @author gary.liu
 * @date 2017/5/29
 */
public class OutOfMemoryPractice {

    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<byte[]>();
        int i = 0;
        boolean flag = true;
        while (flag){
            try {
                i++;
                list.add(new byte[1024 * 1024]);//每次增加一个1M大小的数组对象
            }catch (Throwable e){
                e.printStackTrace();
                flag = false;
                System.out.println("count="+i);//记录运行的次数
            }
        }
    }
}

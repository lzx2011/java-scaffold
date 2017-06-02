package com.lzhenxing.javascaffold.javabase.grammar;

import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gary on 16/8/20.
 */
public class StringTest {

    @Test
    public void subString(){

        String dir = "/Users/gary/Documents/storeUpload/";
        String relativePath = "sfsf/4234/4234.jpg";
        String path = dir + relativePath;
        String directory = path.substring(0,path.lastIndexOf("/"));
        File dirFile = new File(directory);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }

    }

    /**
     * flush() 函数用法
     */
    @Test
    public void useFlush(){
        String s = "Hello World";
        try {
            // create a new stream at specified file
            PrintWriter pw = new PrintWriter(System.out);
            // write the string in the file
            pw.write(s);
//            // flush the writer 使用该方法才会有输出,缓冲区满时才会输出,该方法会强制刷出缓冲区的内容
            pw.flush();
            pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 测试缓冲区大小
     */
    @Test
    public void testBufferSize(){
        char[] array  = new char[8192];
        //char[] array  = new char[8193];
        Arrays.fill(array,'s');
        PrintWriter pw = new PrintWriter(System.out);
        pw.write(array);
        pw.close();
    }

    @Test
    public void splitTest(){
        String test = "sdfsdf,sdfsf,";
        String my = test.substring(0, test.lastIndexOf(","));
        System.out.println(my);

        //String impresss = "好看^美丽";
        String impresss = "sdfsfd^sdfsf";
        List<String> list = Arrays.asList(impresss.split("\\^"));
        System.out.println(list.toString());
    }

    @Test
    public void stringReferenceTest(){
        String a = "abcd";
        String b = "abcd";
        String c = new String("abcd");
        String d = new String("abcd");
        //intern()
        //如果常量池中存在当前字符串, 就会直接返回当前字符串. 如果常量池中没有此字符串, 会将此字符串放入常量池中后, 再返回
        String e = new String("abcd").intern();
        String f = new String("abcd").intern();
        System.out.println(a == b);
        System.out.println(c == d);
        System.out.println(e == f);  //true
    }

    public static void main(String[] args){
        String string;
        Integer i;
    }
}

package javabase.io;

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

    public static void main(String[] args){
        System.out.println();
    }
}

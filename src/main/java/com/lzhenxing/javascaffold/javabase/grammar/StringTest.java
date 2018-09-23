package com.lzhenxing.javascaffold.javabase.grammar;

import com.google.common.collect.Lists;
import com.lzhenxing.javascaffold.entity.PictureDO;
import com.lzhenxing.javascaffold.entity.User;
import com.lzhenxing.javascaffold.util.json.FastJsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;

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

    public static void equalTest(){
        String test = "test";
        String test1 = null;
        System.out.println(test.equals(test1));
    }

    public static void stringTest(){
        Object object = null;
        String test = (String)object;
        System.out.println(test);
        //System.out.println(object.toString());
    }

    @Test
    public void lineNum(){
        //String policy = "儿童及加床政策\t\n"
        //    + "•酒店允许携带儿童入住\n"
        //    + "•每间客房最多容纳1名0-5岁儿童，和成人共用现有床铺。\n"
        //    + "0-5岁\t1名\t使用现有床铺免费\n"
        //    + "•每间客房最多容纳1张加床\n"
        //    + "膳食安排\t酒店不提供早餐\n"
        //    + "出行提示\n"
        //    + "住客必须使用预订确认邮件上的联系方式，在抵达前 24 小时联系酒店，提供详细到达信息。\n"
        //    + "12 月 24 日住宿的总价中包括了必须支付的圣诞前夕晚宴的费用。\n"
        //    + "12 月 31 日住宿的总价中包括了必须支付的元旦前夜晚宴费用。\n"
        //    + "只有经过登记的住客才可进入客房。\n"
        //    + "此酒店不允许携带宠物，包括服务类动物（例如导盲犬）。\n"
        //    + "\n"
        //    + "额外费用\n"
        //    + "以下费用包含在您的房价总额中：\n"
        //    + "元旦前夜晚宴费用\n"
        //    + "圣诞前夜晚宴费用\n"
        //    + "这里已包括酒店所提供的全部收费。但是，收费可能会因不同情况而有所变化，例如，不同住宿天数或客房类型则收费也不同。";
        double score;
        //String policy = "";
        String policy = null;

        //List<String> splitList = Arrays.asList(policy.split("[\n|\r]"));
        List<String> splitList = Lists.newArrayList(policy.split("[\n|\r]"));
        //Collections.addAll(splitList, policy.split("[\n|\r]"));
        splitList.removeIf(String::isEmpty);
        int lineNum = splitList.size();
        if(lineNum > 6){
            score = 100;
        }else {
            score = lineNum * 15;
        }
        System.out.println(lineNum + "," + score);
    }

    @Test
    public void stringBuilderTest(){
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString());
        System.out.println((String)null);
        //Long.toString(null);
    }

    /**
     * 后面几个数如果没值是非常危险的
     */
    @Test
    public void testSplit(){
        String test = "950743|905032|950743|3|南岛|南岛|Ko Surin Tai|南岛|||||0|1||";
        String[] strArr = test.split("\\|", -1);
        System.out.println(strArr.length);
    }




    public static void main(String[] args){
        //String string;
        //Integer i;
        //equalTest();
        //stringTest();】
        //jsonParseTest();
        Object test = null;
        System.out.println((User)test);
    }
}

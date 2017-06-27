package com.lzhenxing.javascaffold.javabase.classloader;

/**
 * ClassName: ClassLoaderPractice <br/>
 * Function: 类加载器父子关系<br/>
 *
 * @author gary.liu
 * @date 2017/6/26
 */
public class ClassLoaderPractice {

    public static void test(){
        //application class loader
        System.out.println(ClassLoader.getSystemClassLoader());
        //extensions class loader
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        //bootstrap class loader
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
    }

    public static void main(String[] args){

        test();
    }

}

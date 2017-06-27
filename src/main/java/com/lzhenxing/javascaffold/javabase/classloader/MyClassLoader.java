package com.lzhenxing.javascaffold.javabase.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * ClassName: MyClassLoader <br/>
 * Function: <br/>
 *
 * 参考:http://www.cnblogs.com/sunniest/p/4574080.html
 *
 * @author gary.liu
 * @date 2017/6/26
 */
public class MyClassLoader extends ClassLoader{

    private String rootPath;

    public MyClassLoader(String rootPath){
        this.rootPath = rootPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //check if the class have been loaded
        Class<?> c = findLoadedClass(name);
        if(c!=null){
            return c;
        }
        //load the class
        byte[] classData = getClassData(name);
        if(classData==null){
            throw new ClassNotFoundException();
        }
        else{
            c = defineClass(name,classData, 0, classData.length);
            return c;
        }
    }

    private byte[] getClassData(String className){
        String path = rootPath+"/"+className.replace('.', '/')+".class";

        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(path);
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp = 0;
            while((temp = is.read(buffer))!=-1){
                bos.write(buffer,0,temp);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                is.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception{

        String rootPath = "/Users/gary/Documents/Study/Github/java-scaffold/build/classes/main/com/lzhenxing/javascaffold/javabase/classloader";
        MyClassLoader loader = new MyClassLoader(rootPath);
        Class<?> c = loader.loadClass("ClassLoaderPractice");
        System.out.println(c.getClassLoader());


    }

}
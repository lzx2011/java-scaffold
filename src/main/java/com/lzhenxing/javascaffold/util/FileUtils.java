package com.lzhenxing.javascaffold.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gary on 16/8/30.
 */
public class FileUtils {

    public static final String FILE_FULL_NAME = "fullName";
    public static final String FILE_NAME = "name";
    public static final String FILE_SUFFIX = "suffix";



    public static void suffixToLower(File path){

        if(path.isFile()){

            fileSuffixToLower(path);
        }else{

            File[] files = path.listFiles();
            for(File f : files){
                if(f.isFile()){

                    fileSuffixToLower(f);
                }else if(f.isDirectory()){

                    suffixToLower(f);
                }
            }
        }
    }

    public static void fileSuffixToLower(File file){

        if(file.isFile()){

            String fileName = file.getName();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            file.renameTo(new File(file.getParent() + "/" + name + "." + suffix.toLowerCase()));
        }
    }

    public static Map<String, String> getFileInfo(File file){
        Map<String, String> fileInfoMap = new HashMap<>();
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        fileInfoMap.put(FILE_FULL_NAME, fileName);
        fileInfoMap.put(FILE_SUFFIX, suffix);
        fileInfoMap.put(FILE_NAME, name);
        return fileInfoMap;
    }

    /**
     * 创建指定目录
     * /apps/dat/2/1/  最后一定要有/
     * @param path
     */
    public static void createPath(String path) {
        int index = path.indexOf(File.separator);
        while(index>-1){
            String tempPath = path.substring(0, index);
            File file = new File(tempPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            index = path.indexOf(File.separator, index + 1);
        }
    }

    public static void getFilePathList(String localPath, List<String> filePathList){
        File file = new File(localPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        //System.out.println("文件夹:" + file2.getAbsolutePath());
                        getFilePathList(file2.getAbsolutePath(),filePathList);
                    } else {
                        if(!file2.getName().startsWith(".")){
                            //System.out.println("文件:" + file2.getAbsolutePath());
                            filePathList.add(file2.getAbsolutePath());
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static List<String> getImageFilePathList(String filePath, String picSuffix){
        List<String> filePathList = new ArrayList<>();
        List<String> picList = new ArrayList<>();
        FileUtils.getFilePathList(filePath, filePathList);
        if(!filePathList.isEmpty()){
            for(String path : filePathList){
                if(picSuffix.equalsIgnoreCase(FileUtils.getFileSuffix(path))){
                    picList.add(path);
                }
            }
        }
        //System.out.println(picList.toString());
        return picList;
    }


    /**
     * 获取文件后缀
     * @param filePath
     * @return
     */
    public static String getFileSuffix(String filePath){
        if(StringUtils.isBlank(filePath) || !filePath.contains(".")){
            return null;
        }
        int index = filePath.lastIndexOf(".");
        return filePath.substring(index+1);
    }

    public static void main(String[] args){

        String path = "/Users/gary/Documents/10005582/compress/testCompress/6939520620549/1.jpg";
        //File file = new File(path);
        //System.out.println(file.getParent());
        //String parentPath = file.getParent();
        //String dir = parentPath.substring(parentPath.lastIndexOf(File.separator)+1);
        //System.out.println(dir);
        //suffixToLower(file);
        //System.out.println(getFileInfo(file).toString());
        //获取所有文件
        //String dir = "/Users/gary/Documents/UploadTest";
        //List<String> filePathList = new ArrayList<>();
        //getFilePathList(dir, filePathList);
        //System.out.println(filePathList.toString());
        //获取后缀
        String filePath = "/sdfsdf/sfsdf/ewfsf.jpg";
        System.out.println(FileUtils.getFileSuffix(filePath));
    }
}

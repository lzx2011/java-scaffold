package com.lzhenxing.javascaffold.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gary on 16/8/30.
 */
public class FileUtils {

    public final static Logger logger = LoggerFactory.getLogger(FileUtils.class);


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

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        if(!sourceFile.exists()) return ;

        FileInputStream in = new FileInputStream(sourceFile);
        FileOutputStream out = new FileOutputStream(targetFile);
        byte[] buffer = new byte[102400];
        while (true) {
            int ins = in.read(buffer);
            if (ins == -1) {
                in.close();
                out.flush();
                out.close();
                break;
            } else {
                out.write(buffer, 0, ins);
            }
        }
    }

    /**
     * 复制文件夹
     * @param sourceDir 源文件夹
     * @param targetDir 目标文件夹
     * @throws IOException
     */
    public static void copyDirectiory(File sourceDir, File targetDir) throws IOException {
        if (!sourceDir.exists()) return ;
        // 新建目标目录
        targetDir.mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = sourceDir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(targetDir.getAbsolutePath() + File.separator + file[i].getName());
                FileUtils.copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 复制的源文件夹
                File dir1 = new File(sourceDir + File.separator + file[i].getName());
                // 复制的目标文件夹
                File dir2 = new File(targetDir + File.separator + file[i].getName());
                FileUtils.copyDirectiory(dir1, dir2);
            }
        }
    }

    public static boolean deleteFile(File file) {
        // 判断是文件还是目录
        if (file.exists()) {
            if (file.isDirectory()) {
                // 若目录下没有文件则直接删除
                if (file.listFiles().length == 0) {
                    file.delete();
                } else {
                    // 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = file.listFiles();
                    int len = file.listFiles().length;
                    for (int j = 0; j < len; j++) {
                        if (delFile[j].isDirectory()) {
                            // 递归调用del方法并取得子目录路径
                            deleteFile(new File(delFile[j].getAbsolutePath()));
                        }
                        // 删除文件
                        delFile[j].delete();
                    }
                    file.delete();
                }
            } else {
                file.delete();
            }
        }
        return true;
    }

    /**
     *
     * deleteAllByPath:根据文件目录删除其下全部文件跟文件夹
     *
     * @param path
     */
    public static void deleteAllByPath(String path) {
        try {
            File file = new File(path);
            if (!file.canWrite()) {
                file.setWritable(true);
            }
            if (file.isDirectory()) {
                String[] fs = file.list();
                for (String f : fs) {
                    deleteAllByPath(path + "\\" + f);
                }
            }
            file.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * getFlies:获取文件下面的子文件
     *
     * @param path
     * @return
     */
    public static File[] getFlies(String path) {
        try {
            File file = new File(path);
            return file.listFiles();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }




    /**
     *
     * saveFile: 保存文件
     *
     * @param file
     * @param filePath
     */
    public static void saveFile(MultipartFile file, String filePath) {
        OutputStream fos = null;
        InputStream is = null;
        try {
            fos = new FileOutputStream(filePath);
            is = file.getInputStream();
            byte[] b = new byte[1024];
            while (is.read(b) != -1) {
                fos.write(b);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            try {
                is.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     *
     * getFileSizes:取得文件大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static int getFileSizes(File f) {
        int s = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            s = fis.available();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return s;
    }

    /**
     *
     * getFileBytes: 从文件里面获取字符流
     *
     * @param f
     * @return
     */
    public static byte[] getFileBytes(File f) {
        int s = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            s = fis.available();
            byte[] b = new byte[s];
            fis.read(b);
            return b;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            return null;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     *
     * getCurrentRandomFileName: 根据当前时间还有随机数获取 唯一的文件名
     *
     * @return
     */
    public static String getCurrentRandomFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHms");//20160126195117
        String currentDate = format.format(new Date());
        Random random = new Random();
        int rint = random.nextInt(Integer.MAX_VALUE);
        return currentDate + rint;
    }


    /**
     *
     * getFileMB:根据byte转换成mb
     *
     * @param byteFile
     * @return
     */
    public static String getFileMB(long byteFile) {
        if (byteFile == 0){
            return "0MB";
        }
        long mb = 1024l * 1024l;
        return "" + byteFile / mb + "MB";
    }

    /**
     * 将byte数组写入文件
     * @param path
     * @param content
     * @throws IOException
     */
	public static void createFile(String path, byte[] content) {

        try(FileOutputStream fos = new FileOutputStream(path)){

            fos.write(content);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

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
        //String filePath = "/sdfsdf/sfsdf/ewfsf.jpg";
        //System.out.println(FileUtils.getFileSuffix(filePath));

        //创建文件
        byte[] bytes = "mysfsfs,sdfsdf".getBytes();
        String filePath = "/Users/gary/Documents/test.txt";
        FileUtils.createFile(filePath, bytes);
    }
}

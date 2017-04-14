package com.lzhenxing.javascaffold.javabase.thread;

import java.io.*;

/**
 * Created by gary on 2017/3/15.
 */
public class ProcessPractice {

    public int createProcessByRunTime() throws IOException{
        int resultCode = -1;
        Process process = Runtime.getRuntime().exec("top", null, new File("/Users/gary/Documents/workspace"));
        //获取进程执行后结果
        try(InputStream inputStream = process.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            byte[] buf = new byte[1024];
            int length;
            while((length = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, length);
            }
            String result = outputStream.toString("utf-8");
            System.out.println(result);
            //阻塞当前线程直到进程执行结束,执行成功返回0
            resultCode = process.waitFor();
            //立即返回执行结果,进程没有结束,可能会抛异常 IllegalThreadStateException
            //resultCode = process.exitValue();
            return resultCode;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return resultCode;
    }

    public int createProcessByProcessbuilder() throws IOException{
        int resultCode = -1;
        ProcessBuilder processBuilder = new ProcessBuilder("ls");
        //默认为FALSE,设为true后,标准错误将与标准输出合并,通过getInputStream获取输出信息
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.directory(new File("/Users/gary/Documents/workspace")).start();
        //获取进程执行后结果
        try(InputStream inputStream = process.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            byte[] buf = new byte[1024];
            int length;
            while((length = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, length);
            }
            String result = outputStream.toString("utf-8");
            System.out.println(result);
            //阻塞当前线程直到进程执行结束
            resultCode = process.waitFor();
            //立即返回执行结果,进程没有结束,可能会抛异常 IllegalThreadStateException
            //resultCode = process.exitValue();
            return resultCode;
        }catch(InterruptedException e) {
            e.printStackTrace();
        }

        return resultCode;
    }

    public static void main(String[] args) throws Exception{

        ProcessPractice practice = new ProcessPractice();
        System.out.println(practice.createProcessByRunTime());
        //System.out.println(practice.createProcessByProcessbuilder());
    }
}

package com.lzhenxing.javascaffold.javabase.exception;

/**
 * ClassName: StackOverFlowPractice <br/>
 * Function: 栈溢出示例<br/>
 *
 * 参考:
 * Java8内存模型—永久代(PermGen)和元空间(Metaspace)
 * http://www.cnblogs.com/paddix/p/5309550.html
 *
 * @author gary.liu
 * @date 2017/5/29
 */
public class StackOverFlowPractice {

    private int num;  //实例化后默认 num=0

    public void increaseNum(){

        num++;
        increaseNum();
    }

    public static void main(String[] args){

        StackOverFlowPractice practice = new StackOverFlowPractice();
        try{
            practice.increaseNum();

        } catch (Throwable e){
            System.out.println("stack deep: " + practice.num);

            e.printStackTrace();
        }
    }

}

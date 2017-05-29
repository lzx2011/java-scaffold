package com.lzhenxing.javascaffold.javabase.thread;

/**
 * ClassName: CurrentPractice <br/>
 * Function: 并发练习,看调三个方法后是否会输出error<br/>
 *
 * 参考:http://gblog.sherlocky.com/java-yuan-zi-cao-zuo-yu-bing-fa/
 *
 * @author gary.liu
 * @date 2017/5/29
 */
public class CurrentPractice {

    private volatile long b = 0; //注意这里加了 volatile 才会输出error,因为线程间改变有可见性

    public void set1() {
        b = 0;
    }

    public void set2() {
        b = -1;
    }

    public void check() {
        System.out.println(b);

        if (0 != b && -1 != b) {  //这里是非原子操作的,多线程下 b 的值可能一直在变
            System.err.println("Error");
        }
    }

    public static void main(final String[] args) {
        final CurrentPractice v = new CurrentPractice();

        // 线程 1：设置 b = 0
        final Thread t1 = new Thread() {
            public void run() {
                while (true) {
                    v.set1();
                }
            };
        };
        t1.start();

        // 线程 2：设置 b = -1
        final Thread t2 = new Thread() {
            public void run() {
                while (true) {
                    v.set2();
                }
            };
        };
        t2.start();

        // 线程 3：检查 0 != b && -1 != b
        final Thread t3 = new Thread() {
            public void run() {
                while (true) {
                    v.check();
                }
            };
        };
        t3.start();
    }
}

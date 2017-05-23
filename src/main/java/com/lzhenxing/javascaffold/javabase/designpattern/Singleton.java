package com.lzhenxing.javascaffold.javabase.designpattern;

/**
 * Created by gary on 16/10/5.
 *
 * 饿汉式单例
 */
public class Singleton {

    //final 保证 instance 不能再改变引用
	private static final Singleton instance = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return instance;
	}
}

/**
 * 懒汉式单例
 */
class LazySingleton {

    private volatile static LazySingleton lazySingleton = null;

    private LazySingleton(){

    }

    public static LazySingleton getInstance(){

        if(lazySingleton == null){
            synchronized (LazySingleton.class){
                if(lazySingleton == null){
                    lazySingleton = new LazySingleton();
                }

            }
        }

        return lazySingleton;
    }
}

package javabase.designpattern;

/**
 * Created by gary on 16/10/5.
 *
 * 饿汉式单例
 */
public class Singleton {

	private static final Singleton instance = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return instance;
	}
}

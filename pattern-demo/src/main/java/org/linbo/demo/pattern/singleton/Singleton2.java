package org.linbo.demo.pattern.singleton;

/**
 * 饿汉模式
 * 1、私有构造方法
 * 2、静态对象实例，初始化时进行实例化
 * 3、静态获取实例方法
 * 
 * 这种方法和直接实例化性能一样
 * @author 71085
 *
 */
public class Singleton2 {
	
	private static Singleton2 instance;
	
	static {
		instance = new Singleton2();
	}
	
	private Singleton2() {}
	
	public static Singleton2 getInstance() {
		return instance;
	}
	
}

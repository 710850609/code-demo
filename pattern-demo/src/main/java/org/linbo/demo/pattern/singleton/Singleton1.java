package org.linbo.demo.pattern.singleton;

/**
 * 饿汉模式
 * 1、私有构造方法
 * 2、静态对象实例，初始化时进行实例化
 * 3、静态获取实例方法
 * 
 * 这种方法在第一次加载的时候进行实例化，如果对象实例过程比较复杂，会有性能问题
 * @author 71085
 *
 */
public class Singleton1 {
	
	private static Singleton1 instance = new Singleton1();
	
	private Singleton1() {}
	
	public static Singleton1 getInstance() {
		return instance;
	}
	
}

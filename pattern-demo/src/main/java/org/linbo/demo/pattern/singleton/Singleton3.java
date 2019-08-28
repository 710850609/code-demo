package org.linbo.demo.pattern.singleton;

/**
 * 懒汉模式
 * 1、私有构造方法
 * 2、静态对象实例，在第一次调用获取实例静态方法时进行实例化，这里需要关注线程安全问题
 * 3、静态获取实例方法
 * @author 71085
 *
 */
public class Singleton3 {
	
	private static Singleton3 instance;
	
	private Singleton3() {}
	
	/**
	 * 对方法上锁，性能差
	 * @return
	 */
	public static synchronized Singleton3 getInstance() {
		if (instance == null) {
			instance = new Singleton3();
		}
		return instance;
	}
	
}

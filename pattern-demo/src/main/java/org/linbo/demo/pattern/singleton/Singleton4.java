package org.linbo.demo.pattern.singleton;

/**
 * 懒汉模式
 * 1、私有构造方法
 * 2、静态对象实例，在第一次调用获取实例静态方法时进行实例化，这里需要关注线程安全问题
 * 3、静态获取实例方法
 * @author 71085
 *
 */
public class Singleton4 {
	
	private static Singleton4 instance;
	
	private Singleton4() {}
	
	/**
	 * 这种是性能比较好的懒汉模式
	 * @return
	 */
	public static Singleton4 getInstance() {
		if (instance == null) {
			synchronized (Singleton4.class) {
				if (instance == null) {
					instance = new Singleton4();
				}
			}
		}
		return instance;
	}
	
}

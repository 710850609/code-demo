package org.linbo.demo.pattern.singleton;

/**
 * 使用枚举实现，即屏蔽了线程安全问题，又实现简单
 * @author 71085
 *
 */
public enum Singleton5 {

	INSTANCE;
	
	private Singleton5() {}
	
	public void doSth1() {
		System.out.println("do some thing 1");
	}
	
	public void doSth2() {
		System.out.println("do some thing 2");
	}
	
	public void doSth3() {
		System.out.println("do some thing 3");
	}
}

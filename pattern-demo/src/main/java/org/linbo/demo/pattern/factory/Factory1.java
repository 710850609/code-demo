package org.linbo.demo.pattern.factory;

/**
 * 工厂模式1
 * 创建复杂对象
 * @author 71085
 *
 */
public class Factory1 {

	public Object getComplexObject() {
		System.out.println("工厂模式生成复杂对象，屏蔽复杂过程");
		return new Object();
	}
	
	public static void main(String[] args) {
		Factory1 f = new Factory1();
		f.getComplexObject();
	}
}

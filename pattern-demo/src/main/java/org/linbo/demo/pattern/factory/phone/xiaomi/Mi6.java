package org.linbo.demo.pattern.factory.phone.xiaomi;

import org.linbo.demo.pattern.factory.phone.Phone;

/**
 * 小米6 手机
 * @author 71085
 *
 */
public class Mi6 implements Phone {

	public static final String ID = "Mi6";
	
	@Override
	public void welcome() {
		System.out.println("欢迎使用 " + ID);
	}

}

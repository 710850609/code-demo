package org.linbo.demo.pattern.factory.phone.iphone;

import org.linbo.demo.pattern.factory.phone.Phone;

/**
 * IPhone Xs 手机
 * @author 71085
 *
 */
public class IPhoneXs implements Phone {

	public static final String ID = "IPhone Xs";
	
	@Override
	public void welcome() {
		System.out.println("欢迎使用 " + ID);
	}

}

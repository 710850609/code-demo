package org.linbo.demo.pattern.factory.phone.iphone;

import org.linbo.demo.pattern.factory.phone.Phone;

/**
 * IPhone XR 手机
 * @author 71085
 *
 */
public class IPhoneXR implements Phone {

	public static final String ID = "IPhone XR";
	
	@Override
	public void welcome() {
		System.out.println("欢迎使用 " + ID);
	}

}

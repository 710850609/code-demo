package org.linbo.demo.pattern.factory.phone.iphone;

import org.linbo.demo.pattern.factory.phone.Phone;
import org.linbo.demo.pattern.factory.phone.PhoneFactory;

/**
 * 工厂模式2
 * 根据不同参数，获取具有相同接口对象的实例
 * iPhone工厂，根据不同手机型号，得到不同型号苹果手机
 * @author 71085
 *
 */
public class IPhoneFactory implements PhoneFactory {
	
	public static final String ID = "IPhone";

	@Override
	public Phone getPhone(String no) {
		if (no == null) {
			return null;
		}
		switch (no) {
		case IPhoneX.ID:
			return new IPhoneX();
		case IPhoneXs.ID:
			return new IPhoneXs();
		case IPhoneXR.ID:
			return new IPhoneXR();
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {
		IPhoneFactory factory = new IPhoneFactory();
		Phone phone = factory.getPhone(IPhoneX.ID);
		phone.welcome();
		phone = factory.getPhone(IPhoneXs.ID);
		phone.welcome();
		phone = factory.getPhone(IPhoneXR.ID);
		phone.welcome();
	}
}

package org.linbo.demo.pattern.factory.phone.xiaomi;

import org.linbo.demo.pattern.factory.phone.Phone;
import org.linbo.demo.pattern.factory.phone.PhoneFactory;

/**
 * 小米工厂
 * 
 * @author 71085
 *
 */
public class XiaoMiFactory implements PhoneFactory {
	
	public static final String ID = "XiaoMi";

	@Override
	public Phone getPhone(String no) {
		if (no == null) {
			return null;
		}
		switch (no) {
		case Mi6.ID:
			return new Mi6();
		case Mi8.ID:
			return new Mi8();
		case Mi8Se.ID:
			return new Mi8Se();
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {
		XiaoMiFactory factory = new XiaoMiFactory();
		Phone phone = factory.getPhone(Mi6.ID);
		phone.welcome();
		phone = factory.getPhone(Mi8.ID);
		phone.welcome();
		phone = factory.getPhone(Mi8Se.ID);
		phone.welcome();
	}

}

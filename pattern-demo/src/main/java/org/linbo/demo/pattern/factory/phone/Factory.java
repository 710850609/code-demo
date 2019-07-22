package org.linbo.demo.pattern.factory.phone;

import org.linbo.demo.pattern.factory.phone.iphone.IPhoneFactory;
import org.linbo.demo.pattern.factory.phone.iphone.IPhoneX;
import org.linbo.demo.pattern.factory.phone.xiaomi.Mi6;
import org.linbo.demo.pattern.factory.phone.xiaomi.XiaoMiFactory;

public class Factory {

	public static PhoneFactory getPhoneFactory(String no) {
		if (no == null) {
			return null;
		}
		switch (no) {
		case XiaoMiFactory.ID:
			return new XiaoMiFactory();
		case IPhoneFactory.ID:
			return new IPhoneFactory();
		default:
			return null;
		}
	}
	
	public static void main(String[] args) {
		PhoneFactory factory = Factory.getPhoneFactory(XiaoMiFactory.ID);
		factory.getPhone(Mi6.ID).welcome();
		
		factory = Factory.getPhoneFactory(IPhoneFactory.ID);
		factory.getPhone(IPhoneX.ID).welcome();
	}

}

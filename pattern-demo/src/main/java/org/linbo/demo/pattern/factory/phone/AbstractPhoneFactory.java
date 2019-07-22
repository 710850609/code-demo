package org.linbo.demo.pattern.factory.phone;

/**
 * 抽象工厂
 * @author 71085
 *
 */
public interface AbstractPhoneFactory {

	/**
	 * 根据不同厂商标识，获取对应的生产工厂
	 * @param no
	 * @return
	 */
	PhoneFactory getFactory(String no);
}

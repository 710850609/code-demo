package org.linbo.demo.pattern.factory.phone;

/**
 * 生产工厂的抽象
 * @author 71085
 *
 */
public interface PhoneFactory {

	/**
	 * 根据型号，获取对应的手机
	 * @param no
	 * @return
	 */
	Phone getPhone(String no);
}

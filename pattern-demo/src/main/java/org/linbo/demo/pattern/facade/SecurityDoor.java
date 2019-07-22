package org.linbo.demo.pattern.facade;

/**
 * 安全门：一种门
 * @author 71085
 *
 */
public class SecurityDoor implements IDoor {

	@Override
	public void open() {
		System.out.println("安全门打开了，需要两把钥匙");
	}

}

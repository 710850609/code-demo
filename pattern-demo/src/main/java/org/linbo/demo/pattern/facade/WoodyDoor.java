package org.linbo.demo.pattern.facade;

/**
 * 木门：一种门
 * @author 71085
 *
 */
public class WoodyDoor implements IDoor {

	@Override
	public void open() {
		System.out.println("木门开门了，不需要钥匙");
	}

}

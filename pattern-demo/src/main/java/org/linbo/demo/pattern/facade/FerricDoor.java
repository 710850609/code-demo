package org.linbo.demo.pattern.facade;

/**
 * 铁门：一种门
 * @author 71085
 *
 */
public class FerricDoor implements IDoor {

	@Override
	public void open() {
		System.out.println("铁门打开了，需要一把钥匙");
	}

}

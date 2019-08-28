package org.linbo.demo.pattern.facade;

public class Main {

	public static void main(String[] args) {
		IDoor door = new WoodyDoor();
		door.open();
		
		door = new FerricDoor();
		door.open();
		
		door = new SecurityDoor();
		door.open();
	}

}

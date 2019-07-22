package org.linbo.demo.pattern.subscriber;

import org.linbo.demo.pattern.subscriber.intf.Bus;
import org.linbo.demo.pattern.subscriber.intf.IPublisher;
import org.linbo.demo.pattern.subscriber.intf.Message;

public class Publisher implements IPublisher {

	/**
	 * 发布者标识
	 */
	public static final String UID = Publisher.class.getSimpleName();
	
	public Publisher() {
		registry();
	}
	
	/**
	 * 向总线注册
	 */
	private void registry() {
		Bus.registryPublisher(UID);
	}

	public void produce(Message msg) {
		Bus.reciveMessage(UID, msg);
	}

}

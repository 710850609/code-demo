package org.linbo.demo.pattern.subscriber;

import org.linbo.demo.pattern.subscriber.intf.Bus;
import org.linbo.demo.pattern.subscriber.intf.ISubscriber;
import org.linbo.demo.pattern.subscriber.intf.Message;

/**
 * 订阅者B
 * @author 71085
 *
 */
public class SubscriberB implements ISubscriber {
	
	public SubscriberB() {
		registry();
	}
	
	/**
	 * 向总线注册
	 */
	private void registry() {
		Bus.subscribe(this, Publisher.UID);
	}
	
	public void doSomeThing(Message msg) {
		System.out.println(this.getClass().getSimpleName() + "@" + this.hashCode() + "接到信息:" + msg + "，可以还房租了");
	}

}

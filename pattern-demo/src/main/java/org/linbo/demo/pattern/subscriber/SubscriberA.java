package org.linbo.demo.pattern.subscriber;

import org.linbo.demo.pattern.subscriber.intf.Bus;
import org.linbo.demo.pattern.subscriber.intf.ISubscriber;
import org.linbo.demo.pattern.subscriber.intf.Message;

/**
 * 订阅者A
 * @author 71085
 *
 */
public class SubscriberA implements ISubscriber {

	public SubscriberA() {
		registry();
	}
	
	/**
	 * 向总线注册
	 */
	private void registry() {
		Bus.subscribe(this, Publisher.UID);
	}
	
	@Override
	public void doSomeThing(Message msg) {
		System.out.println(this.getClass().getSimpleName() + "@" + this.hashCode() + "接到信息:" + msg + "，今晚可以加鸡腿了");
	}

}

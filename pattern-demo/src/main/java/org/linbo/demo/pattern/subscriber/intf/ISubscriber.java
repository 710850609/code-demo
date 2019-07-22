package org.linbo.demo.pattern.subscriber.intf;

/**
 * 订阅者
 * @author 71085
 *
 */
public interface ISubscriber {
	
	/**
	 * 接受到事件后的回调
	 */
	public void doSomeThing(Message msg);
	
}

package org.linbo.demo.pattern.subscriber.intf;

/**
 * 发布者
 * @author 71085
 *
 */
public interface IPublisher {
	
	/**
	 * 发布事件
	 * @param msg 消息
	 */
	public void produce(Message msg);
}

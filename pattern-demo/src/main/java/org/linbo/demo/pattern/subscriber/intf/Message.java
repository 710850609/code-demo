package org.linbo.demo.pattern.subscriber.intf;

/**
 * 消息，发布者生成的对象，订阅者消费的对象
 * @author 71085
 *
 */
public class Message {

	private String data;

	public Message(String msg) {
		this.data = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return data;
	}
	
}

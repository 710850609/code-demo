package org.linbo.demo.pattern.subscriber.intf;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息总线，接收发布者生产的消息，并转发给对应的订阅者
 * @author 71085
 *
 */
public class Bus {
	
	/**
	 * 订阅队列。key-value： 发布者->所有订阅者
	 */
	private static Map<String, Set<ISubscriber>> map = new ConcurrentHashMap<>();

	/**
	 * 注册发布者
	 * @param uid
	 */
	public static void registryPublisher(String uid) {
		System.out.println("注册发布者：" + uid);
		if (!map.containsKey(uid)) {
			map.put(uid, new HashSet<>());
		}
	}
	
	/**
	 * 订阅者订阅消息类别
	 * @param subscriberId
	 * @param publisherId
	 */
	public static synchronized void subscribe(ISubscriber subscriber, String publisherId) {
		System.out.println(subscriber.getClass().getSimpleName() + "@" + subscriber.hashCode() + "订阅" + publisherId);
		Set<ISubscriber> subscribers = map.get(publisherId);
		if (subscribers == null) {
			subscribers = new HashSet<>();
		}
		subscribers.add(subscriber);
		map.put(publisherId, subscribers);
	}
	
	/**
	 * 接收发布者信息，并转发给订阅者
	 * @param publisherId
	 * @param msg
	 */
	public static void reciveMessage(String publisherId, Message msg) {
		System.out.println(publisherId + "发布信息：" + msg);
		Set<ISubscriber> subscribers = map.get(publisherId);
		if (subscribers != null && !subscribers.isEmpty()) {
			subscribers.forEach(s -> s.doSomeThing(msg));
		}
		System.out.println("完成发布-订阅流程");
	}

	/**
	 * 删除所有订阅者
	 */
	public static synchronized void cleanSubscibers() {
		map.keySet().forEach(k -> map.remove(k));
	}
}

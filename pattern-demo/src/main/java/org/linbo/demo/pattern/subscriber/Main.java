package org.linbo.demo.pattern.subscriber;

import java.util.concurrent.CountDownLatch;

import org.linbo.demo.pattern.subscriber.intf.Bus;
import org.linbo.demo.pattern.subscriber.intf.IPublisher;
import org.linbo.demo.pattern.subscriber.intf.Message;

public class Main {
	
	public static void main(String[] args) throws Exception {
		do1();
		Bus.cleanSubscibers();
		System.out.println("============================");
		do2();
		Bus.cleanSubscibers();
		System.out.println("============================");
		do3();
		Bus.cleanSubscibers();
	}
	
	private static void do1() {
		IPublisher publisher = new Publisher();
		new SubscriberA();
		new SubscriberB();
		publisher.produce(new Message("发工资了"));
	}
	
	private static void do2() throws Exception {
		Thread a = new Thread(() -> new SubscriberA());
		a.start();
		a.join();
		
		Thread b = new Thread(() -> new SubscriberB());
		b.start();
		b.join();
		
		IPublisher publisher = new Publisher();
		publisher.produce(new Message("发工资了"));
	}
	
	private static void do3() throws InterruptedException {
		CountDownLatch cdl = new CountDownLatch(2);

		new Thread(() -> {
			new SubscriberA();
			cdl.countDown();
		}).start();
		new Thread(() -> {
			new SubscriberB();
			cdl.countDown();
		}).start();

		IPublisher publisher = new Publisher();
		cdl.await();
		publisher.produce(new Message("发工资了"));
	}
}

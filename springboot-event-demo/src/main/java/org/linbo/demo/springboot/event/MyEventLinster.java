package org.linbo.demo.springboot.event;

import org.springframework.context.ApplicationListener;

public class MyEventLinster implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("收到事件:" + Thread.currentThread().getName());
    }
}

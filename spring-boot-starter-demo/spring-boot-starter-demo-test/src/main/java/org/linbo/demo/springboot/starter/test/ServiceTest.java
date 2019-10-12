package org.linbo.demo.springboot.starter.test;

import org.linbo.demo.springboot.starter.DemoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LinBo
 * @date 2019-10-12 9:13
 */
@Component
public class ServiceTest {

    @Autowired(required = false)
    private DemoClient client;

    @PostConstruct
    public void doAfter() {
        if (client != null) {
            client.doRequest();
        } else {
            System.out.println();
            System.out.println("未实例化 " + DemoClient.class.getName());
            System.out.println();
        }
    }
}

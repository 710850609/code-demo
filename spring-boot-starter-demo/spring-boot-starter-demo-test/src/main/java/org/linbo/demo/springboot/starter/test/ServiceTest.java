package org.linbo.demo.springboot.starter.test;

import org.linbo.demo.springboot.starter.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LinBo
 * @date 2019-10-12 9:13
 */
@Component
public class ServiceTest {

    @Autowired
    private Demo demo;

    @PostConstruct
    public void printName() {
        String name = demo.getName();
        System.out.println("org.linbo.demo.springboot.starter.Demo#name: ");
        System.out.println(name);
        System.out.println("");
    }
}

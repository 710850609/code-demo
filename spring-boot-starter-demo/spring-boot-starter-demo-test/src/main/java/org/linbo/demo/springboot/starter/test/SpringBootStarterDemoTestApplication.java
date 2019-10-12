package org.linbo.demo.springboot.starter.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootStarterDemoTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterDemoTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}

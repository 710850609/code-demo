package org.linbo.demo.springboot.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootEventDemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootEventDemoApplication.class);
        application.addListeners(new MyEventLinster());
        ConfigurableApplicationContext context = application.run(args);
        int i = 0;
        while (i++ < 100) {
            context.publishEvent(new MyEvent("" + i));
        }
    }

}

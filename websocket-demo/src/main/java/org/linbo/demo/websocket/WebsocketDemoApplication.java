package org.linbo.demo.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketDemoApplication implements CommandLineRunner {

    @Value("${server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(WebsocketDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Windows")) {
            Runtime.getRuntime().exec("cmd /c start http://localhost:" + port);
        }
    }
}

package org.linbo.demo.threadPool.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LinBo
 * @date 2019-10-11 14:38
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private SimpleTask simpleTask;

    @Autowired
    private AnnotationTask annotationTask;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        simpleTask.doTask();

        int count = 10;
        while (count-- >0) {
            annotationTask.doTask();
        }
    }
}


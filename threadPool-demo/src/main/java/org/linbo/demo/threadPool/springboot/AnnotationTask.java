package org.linbo.demo.threadPool.springboot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 使用 @EnableAsync 表示开启异步执行 </br>
 * 使用 @Async 表示该方法的需要进行异步执行 </br>
 * 但 spring基于注解调用有个毛病, 如果是方法内部调用,是不产生效果的. </br>
 * 必须是外部类注入 AnnotationTask, 并以annotationTask.doTask()的方式调用 </br>
 */
@EnableAsync
@Component
public class AnnotationTask {

    @Async
    public void doTask() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

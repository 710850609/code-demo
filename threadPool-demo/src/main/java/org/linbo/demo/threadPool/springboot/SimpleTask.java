package org.linbo.demo.threadPool.springboot;

import org.linbo.demo.threadPool.MyRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LinBo
 * @date 2019-10-12 14:23
 */
@Component
public class SimpleTask {

    /**
     * spring-boot 已经有autoconfig类,
     * org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
     **/
    @Autowired
    private ThreadPoolTaskExecutor executor;

    public void doTask() {
        int i = 10;
        while (i-- > 0) {
            executor.execute(new MyRunner());
        }
    }

}

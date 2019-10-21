package org.linbo.demo.threadPool.spring;

import org.linbo.demo.threadPool.MyRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 使用 <link>org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor</link>
 * </p>
 * 只需依赖
 * <code>
 *     <groupId>org.springframework</groupId>
*      <artifactId>spring-context</artifactId>
 * </code>
 * @author LinBo
 * @date 2019-10-11 11:51
 */
public class SpringThreadExecutor {

    public static ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor= new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(processors);
        executor.setMaxPoolSize(processors);
        // 队列容量
        executor.setQueueCapacity(128);
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("SpringThreadExecutor-");
        executor.initialize();
        return executor;
    }


    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPool = getThreadPoolTaskExecutor();
        int count = 10;
        while (count-- > 0) {
            threadPool.execute(new MyRunner());
        }
    }
}

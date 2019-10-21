package org.linbo.demo.threadPool.jdk;

import org.linbo.demo.threadPool.MyRunner;

import java.util.concurrent.*;

/**
 * 简单版本实现,只依赖jdk
 * 活跃线程数和cpu核心数一致
 * 可自定义线程组前缀
 * @author LinBo
 * @date 2019-10-11 10:07
 */
public class SimpleThreadExecutor {

    private static ThreadPoolExecutor instance = null;

    private SimpleThreadExecutor() {
    }

    static {
        // 获取CPU核心数
        int processors = Runtime.getRuntime().availableProcessors();
        // 构建ThreadFactory
        ThreadFactory threadFactory = new NameableThreadFactory("SimpleThread");
        instance = new ThreadPoolExecutor(processors, processors, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), threadFactory);
    }


    public static ThreadPoolExecutor getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = SimpleThreadExecutor.getInstance();
        int count = 10;
        while (count-- > 0) {
            threadPool.execute(new MyRunner());
        }
    }
}

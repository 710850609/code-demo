package org.linbo.demo.threadPool.jdk;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LinBo
 * @date 2019-10-11 11:36
 */
public class NameableThreadFactory implements ThreadFactory {

    private ThreadGroup threadGroup;
    private AtomicInteger threadId;

    public NameableThreadFactory(String threadGroupName) {
        threadGroup = new ThreadGroup(threadGroupName);
        threadId = new AtomicInteger(0);
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = String.format("%s-%d", this.threadGroup.getName(), threadId.getAndIncrement());
        return new Thread(this.threadGroup, r, threadName);
    }
}

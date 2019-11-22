package org.linbo.demo.lock.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.linbo.demo.lock.DistributedLockException;
import org.linbo.demo.lock.IDistributedLock;
import org.linbo.demo.lock.spring.SpringContextHolder;
import org.springframework.cloud.zookeeper.ZookeeperProperties;

import java.util.concurrent.TimeUnit;

/**
 * @author LinBo
 * @date 2019-11-12 10:01
 */
@Slf4j
public class ZookeeperLock implements IDistributedLock {

    /** zookeeper锁命名空间名称 */
    private static final String ZK_LOCK_NAME_SPACE = "concurrent/locks";

    private InterProcessMutex lock;

    private String zNodeName;

    public ZookeeperLock(String lockName) {
        this.zNodeName = "/" + lockName;
        init();
    }

    private void init() {
        ZookeeperProperties zookeeperProperties = SpringContextHolder.getBean(ZookeeperProperties.class);
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(zookeeperProperties.getBaseSleepTimeMs(),
                zookeeperProperties.getMaxRetries(),
                zookeeperProperties.getMaxSleepMs());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperProperties.getConnectString())
                .namespace(ZK_LOCK_NAME_SPACE)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        // 创建可重入互斥锁
        this.lock = new InterProcessMutex(client, this.zNodeName);
        log.info("初始化zookeeper分布式锁: /{}{}", ZK_LOCK_NAME_SPACE, this.zNodeName);
    }

    @Override
    public void lock() throws DistributedLockException {
        try {
            lock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean tryLock() {
        try {
            this.lock(DEFAULT_TIMEOUT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(long timeout) {
        try {
            this.lock(timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void lock(long timeout) {
        try {
            lock.acquire(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("分布式锁上锁失败", e);
            throw new DistributedLockException(e);
        }
    }

    @Override
    public void unlock() throws DistributedLockException {
        try {
            lock.release();
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("分布式锁解锁失败", e);
            throw new DistributedLockException(e);
        }
    }
}

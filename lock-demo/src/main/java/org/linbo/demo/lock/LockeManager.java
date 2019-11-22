package org.linbo.demo.lock;


import org.linbo.demo.lock.impl.RedisLock;
import org.linbo.demo.lock.impl.ZookeeperLock;

/**
 * 分布式锁维护入口
 * @author LinBo
 * @date 2019-11-19 13:33
 */
public interface LockeManager {

    IDistributedLock ZK_ORDER_NO = new ZookeeperLock("order-no");

    IDistributedLock REDIS_ORDER_NO = new RedisLock("order-no");

}

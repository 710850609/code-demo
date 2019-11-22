package org.linbo.demo.sequence.impl;

import org.linbo.demo.sequence.ISequence;
import org.linbo.demo.sequence.spring.SpringContextHolder;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

/**
 * @author LinBo
 * @date 2019-11-22 12:57
 */
public class RedisCacheSequence implements ISequence<Long> {

    private String namespace;

    private long maxCacheValue = 0;

    private long curValue = 0;
    /** 批次获取数量 */
    private static final long PERIOD = 100;

    private RedissonClient redissonClient;

    public RedisCacheSequence(String namespace) {
        this.namespace = "concurrent:sequence:" + namespace;
        redissonClient = SpringContextHolder.getBean(RedissonClient.class);
    }

    @Override
    public synchronized Long next() {
        if (maxCacheValue - curValue < 10) {
            fetchSegment();
        }
        return curValue++;
    }

    private void fetchSegment() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(namespace);
        curValue = atomicLong.getAndAdd(PERIOD);
        maxCacheValue = curValue + PERIOD;
    }
}

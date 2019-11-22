package org.linbo.demo.lock;

/**
 * @author LinBo
 * @date 2019-11-18 9:34
 */
public class DistributedLockException extends RuntimeException {

    public DistributedLockException(String message) {
        super(message);
    }

    public DistributedLockException(Exception e) {
        super(e);
    }
}

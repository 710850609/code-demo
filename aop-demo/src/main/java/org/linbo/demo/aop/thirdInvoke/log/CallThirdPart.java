package org.linbo.demo.aop.thirdInvoke.log;

/**
 */
@FunctionalInterface
public interface CallThirdPart<T> {

    public T call();
}

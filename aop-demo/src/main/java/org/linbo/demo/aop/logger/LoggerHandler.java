package org.linbo.demo.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 系统日志处理
 **/
@Aspect
@Component
@Slf4j
public class LoggerHandler {
    @Pointcut("execution(* org.linbo.demo.aop.service.*.*(..))")
    public void log(){}

//    @Around("execution(* org.linbo.demo.aop.service.*.*(..))")
    @Around(value = "log()")
    public void doLog(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        try {
            pjp.proceed();
        } catch (Throwable e) {
            log.error("请求 {} 出现异常", method, e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("请求 {}，耗时：{} ms", method, endTime - startTime);
        }
    }
}

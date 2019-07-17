package org.linbo.demo.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.AspectJAopUtils;
import org.springframework.aop.aspectj.AspectJProxyUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * 调用第三方接口日志处理
 **/
@Aspect
@Component
@Slf4j
public class RestControllerLogHandler {

    @Before(value = "@annotation(ctrl)", argNames = "arg")
    public void doBefore(ProceedingJoinPoint pjp, RestController ctrl, Object arg) {
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        log.info("调用{}开始", method);
    }

    @After("@annotation(ctrl)")
    public void doAfter(ProceedingJoinPoint pjp, RestController ctrl) {
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        log.info("调用{}完成", method);
    }

    @AfterReturning("@annotation(ctrl)")
    public void doAfterReturning(ProceedingJoinPoint pjp, RestController ctrl) {
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        log.info("调用{}完成", method);
    }

    @AfterThrowing(value = "@annotation(ctrl)", throwing = "e")
    public void doAfterThrowing(ProceedingJoinPoint pjp, RestController ctrl, Exception e) {
        Signature signature = pjp.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        log.info("调用{}出现异常", method, e);
    }

}

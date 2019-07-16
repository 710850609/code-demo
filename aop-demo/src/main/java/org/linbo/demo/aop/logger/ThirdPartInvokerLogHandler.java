package org.linbo.demo.aop.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 调用第三方接口日志处理
 **/
@Aspect
@Component
@Slf4j
public class ThirdPartInvokerLogHandler {

    public void doLog(ProceedingJoinPoint pjp) {

    }
}

package org.linbo.demo.aop.thirdInvoke.log;

/**
 *  第三方调用日志
 */
public @interface ThirdInvokeLog {

    String method();

    String describe();
}

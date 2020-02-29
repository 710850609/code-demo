package org.linbo.demo.aop.crypt;

import java.lang.annotation.*;

/**
 * 开启请求解密
 * @Author LinBo
 * @Date 2020-02-17 16:50
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptRequest {

    boolean value() default true;
}

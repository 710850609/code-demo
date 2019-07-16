# 注解方式的AOP实现
<span style="color: grey; font-style: italic;">AOP好比微创手术刀，在病人的病灶的位置划开口子，进行治理</span>

## 1、常规实现

### 1.1、基于XML配置
<pre>
SpringBoot的流行趋势，已经开始走向基于注解的自动化配置，已经做到无XML配置文件。所以这里就不介绍XML方式的开发。
</pre>
### 1.2、基于```Aspect```注解
#### 1.2.1、Jar依赖
这里使用```spring-boot-starter-aop```，具体版本号跟随```spring-boot-starter-parent```定义。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
#### 1.2.2、切面
<span>切面 = 切点(```Pointcut```) + 通知(```Before```、```After```、```AfterReturning```、```Around```、```AfterThrowing```)</span>
- 切点支持：基于```execution```表达式、某个注解
- 切面支持：基于```Aspect```

demo例子
```java
@Aspect
@Component
public class LoggerHandler {
    
    @Pointcut("execution(* org.linbo.demo.aop.service.*.*(..))")
    public void log(){}

    @Around(value = "log()")
    public void doLog(ProceedingJoinPoint pjp) throws Throwable {
        // TODO 调用前逻辑
        pjp.proceed();
        // TODO 调用后逻辑
    }
}
```

简化版demo例子
```java
@Aspect
@Component
public class LoggerHandler {
    
    @Around(value = "execution(* org.linbo.demo.aop.service.*.*(..))")
    public void doLog(ProceedingJoinPoint pjp) throws Throwable {
        // TODO 调用前逻辑
        pjp.proceed();
        // TODO 调用后逻辑
    }
}
```

#### 1.2.3、通知
```@org.aspectj.lang.annotation.Aspect```

#### 1.2.4、组织切面



## 2、使用场景
### 1、批量进行拦截处理，调用方法有统一规则的类全名



### 2、对个别方法进行拦截，对应类名没有规则


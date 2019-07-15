# JSR-303 参数校验

### 1、常规使用
- 1.1、请求参数加上符合JSR-303校验注解（包括基本类型和自定义类）。如果请求参数是自定义类，则在类的属性上加校验注解。
- 1.2、请求参数前面加上 ```@javax.validation.Valid``` 注解，或是 ```@org.springframework.validation.annotation.Validated``` 注解，告诉spring框架调用时进行参数校验。
- 1.3、校验注解是在方法入参上，则需要在该方法所在的类上添加 ```@org.springframework.validation.annotation.Validated``` 注解，在入参前或是在方法上添加启用校验注解都不生效。
- 1.4、如果请求参数列表中有 ```BindingResult```，则springmvc框架不会向外抛异常，默认代码自行处理。

### 2、自定义注解
- 2.1、创建自定义注解
```java
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 无敏感词校验注解
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoSensitiveWordsValidator.class)
public @interface NoSensitiveWords {

    String message() default "包含敏感词";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

- 2.2、自定义注解对应的校验类
```java
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 敏感词校验逻辑
 **/
public class NoSensitiveWordsValidator implements ConstraintValidator<NoSensitiveWords, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        // 这里只简单举例校验
        Set<String> sensitiveWords = new HashSet<>();
        sensitiveWords.add("毛泽东");
        sensitiveWords.add("邓小平");
        for (String word : sensitiveWords) {
            if (value.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
```

- 2.3、在类属性或方法入参上使用自定义注解
```java
@Data
public class Account {

    /** 昵称 */
    @Length(min = 2, max = 20)
    @NoSensitiveWords
    private String nickName;
}
```

### 3、自定义异常
- 3.1、校验注解使用在实体参数上时，spring抛出的是```org.springframework.web.bind.MethodArgumentNotValidException```异常。
- 3.2、校验注解使用在方法入参上时，spring抛出的是```javax.validation.ConstraintViolationException```异常。
- 3.3、针对上述两种情况，可以做统一的拦截并封装成统一的系统异常
```java
import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.HttpResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public HttpResult<Object> springValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder buf = new StringBuilder();
        allErrors.forEach(error -> {
            String objectName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            buf.append(objectName).append(":").append(message).append(", ");
        });
        int len = buf.length();
        if (len > 1) {
            buf.delete(len - 2, len);
        }
        HttpResult<Object> data = HttpResult.builder()
                .code("400")
                .message(buf.toString())
                .build();
        log.warn("参数校验错误: {}", data);
        return data;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public HttpResult<Object> jsr303ValidException(ConstraintViolationException e) {
        HttpResult<Object> data = HttpResult.builder()
                .code("400")
                .message(e.getMessage())
                .build();
        log.warn("参数校验错误: {}", data);
        return data;
    }

}
```

### 4、国际化校验异常信息
- 4.1、在resources目录下新增```ValidationMessages.properties```文件，英文为```ValidationMessages_en.properties```，中文为英文为```ValidationMessages_zh_CN.properties```，与标准国际化命名类型，都是ValidationMessages开头命令。
- 4.2、定义信息key和value
- 4.3、在自定义校验注解的```String message() default "{properties中定义的key}"```中，
    或是在使用校验注解时（```@Length(min = 2, max = 20, message = "{properties中定义的key}")```）加入properties中定义的key。

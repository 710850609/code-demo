package org.linbo.demo.validator.annotation;

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

    String message() default "{validator.nick_name.no_sensitive_words}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


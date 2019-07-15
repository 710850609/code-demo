package org.linbo.demo.validator.annotation;

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

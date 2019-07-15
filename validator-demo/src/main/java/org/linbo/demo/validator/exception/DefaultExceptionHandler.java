package org.linbo.demo.validator.exception;

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

/**
 * @author LinBo
 * @date 2019-07-12 10:36
 */
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
        log.error("参数校验错误: {}", data);
        return data;
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public HttpResult<Object> jsr303ValidException(ConstraintViolationException e) {
        HttpResult<Object> data = HttpResult.builder()
                .code("400")
                .message(e.getMessage())
                .build();
        log.error("参数校验错误: {}", data);
        return data;
    }

}

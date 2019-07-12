package org.linbo.demo.validator.exception;

import lombok.extern.slf4j.Slf4j;
import org.linbo.demo.validator.bean.ResultData;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author LinBo
 * @date 2019-07-12 10:36
 */
@ControllerAdvice
@RestController
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResultData<Object> validException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder buf = new StringBuilder();
        allErrors.forEach(error -> {
            String objectName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            buf.append(objectName).append(message).append("\n");
        });
        ResultData<Object> data = ResultData.builder()
                .code("400")
                .message(buf.toString())
                .build();
        log.info("参数校验错误: {}", data);
        return data;
    }

}

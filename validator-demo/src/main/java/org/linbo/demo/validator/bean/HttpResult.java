package org.linbo.demo.validator.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LinBo
 * @date 2019-07-12 14:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HttpResult<T> {

    private String message;

    private String code;

    private T data;
}

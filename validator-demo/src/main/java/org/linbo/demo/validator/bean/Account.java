package org.linbo.demo.validator.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LinBo
 * @date 2019-07-11 16:03
 */
@Data
public class Account {

    /** 用户id */
    private String id;

    /** 姓名 */
    @NotBlank
    private String name;

    /** 昵称 */
    private String nickName;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private Integer gender;

    /** 手机号 */
    @NotBlank
    private String mobile;

    /** 生日 */
    private Date birthday;
}

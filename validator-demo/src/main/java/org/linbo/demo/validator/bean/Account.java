package org.linbo.demo.validator.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.linbo.demo.validator.annotation.NoSensitiveWords;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author LinBo
 * @date 2019-07-11 16:03
 */
@Data
public class Account {

    /** 用户id，只有更新场景才要求不为空 */
    @NotBlank(groups = {Update.class})
    private String id;

    /** 姓名 */
    @NotBlank
    @Length(min = 2, max = 20, message = "{validator.name.length_not_allow}")
    private String name;

    /** 昵称 */
    @Length(min = 2, max = 20)
    @NoSensitiveWords
    private String nickName;

    /** 年龄 */
    @Range(min = 0, max = 300)
    private Integer age;

    /** 性别 */
    @Range(min = 0, max = 1)
    private Integer gender;

    /** 手机号 */
    @NotBlank
    @Length(max = 11)
    private String mobile;

    /** 生日 */
    private Date birthday;

    /** 密码 */
    private String password;
    /** 确认密码 */
    private String passwordConfirm;


    /** 新增场景 */
    public interface Add{}
    /** 更新场景 */
    public interface Update{}
}

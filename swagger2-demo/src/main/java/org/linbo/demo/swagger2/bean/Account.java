package org.linbo.demo.swagger2.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author LinBo
 * @date 2019-07-11 16:03
 */
@Data
@ApiModel(value = "账户")
public class Account {

    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private String id;

    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String name;

    /** 昵称 */
    @ApiModelProperty(value = "昵称")
    private String nickeName;

    /** 年龄 */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /** 生日 */
    @ApiModelProperty(value = "生日")
    private Date birthday;
}

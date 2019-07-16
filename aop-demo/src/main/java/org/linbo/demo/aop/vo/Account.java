package org.linbo.demo.aop.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 账户实体
 **/
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Account {

    /** id */
    private String id;

    /** 姓名 */
    private String name;

    /** 昵称 */
    private String nickName;

    /** 性别 */
    private Integer gender;

    /** 出生日期 */
    private Date birthday;
}

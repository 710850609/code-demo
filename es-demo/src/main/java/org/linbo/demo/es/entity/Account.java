package org.linbo.demo.es.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author LinBo
 * @date 2020/2/7 17:10
 */
@Data
@TableName("account")
public class Account {

    @TableId("id")
    private String id;

    @TableField("name")
    private String name;

    @TableField("pwd")
    private String pwd;

    @TableField("salt")
    private String salt;

    @TableField("gender")
    private String gender;

    @TableField("mobile")
    private String mobile;

    @TableField("state")
    private String state;

    @TableField("email")
    private String email;

    @TableField("id_card_no")
    private String idCardNo;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;

    @TableField("uid")
    private String uid;

    @TableField("last_login_time")
    private String lastLoginTime;

    @TableField("last_login_ip")
    private String lastLoginIp;

    @TableField("error_login_count")
    private String errorLoginCount;

    @TableField("login_count")
    private String loginCount;


}

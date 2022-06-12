package org.linbo.demo.es.entity.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author LinBo
 * @date 2020/2/7 17:10
 */
@Data
@Document(indexName = "account", type = "doc", useServerConfiguration = true, createIndex = false)
public class Account {

    @Id
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    private String pwd;

    private String salt;

    @Field(type = FieldType.Integer, analyzer = "ik_max_word")
    private Integer gender;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String mobile;

    private String state;

    private String email;

    private String idCardNo;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || epoch_millis")
    private Date createTime;

    private Date updateTime;

    private String uid;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd || epoch_millis")
    private Date lastLoginTime;

    private String lastLoginIp;

    private Integer errorLoginCount;

    private Integer loginCount;

}

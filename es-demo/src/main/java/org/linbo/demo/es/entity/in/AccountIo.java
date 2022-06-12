package org.linbo.demo.es.entity.in;

/**
 * @author LinBo
 * @date 2020/2/12 10:04
 */

import lombok.Data;

import java.util.Date;

@Data
public class AccountIo {

    private String name;

    private String mobile;

    private Date createTime;

    private Date updateTime;

}

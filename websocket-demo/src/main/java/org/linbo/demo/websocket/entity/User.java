package org.linbo.demo.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LinBo
 * @date 2020/2/29 22:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String name;
}

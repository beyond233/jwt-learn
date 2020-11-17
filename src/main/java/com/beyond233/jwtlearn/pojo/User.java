package com.beyond233.jwtlearn.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 描述: 用户实体类
 *
 * @author beyond233
 * @since 2020/10/18 19:03
 */
@Data
@Accessors(chain=true)
public class User {

    private Integer id;
    private String name;
    private String password;

}

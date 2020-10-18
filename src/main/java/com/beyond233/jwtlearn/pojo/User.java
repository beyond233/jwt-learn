package com.beyond233.jwtlearn.pojo;

import lombok.Data;

/**
 * 描述: 用户实体类
 *
 * @author beyond233
 * @since 2020/10/18 19:03
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String password;

}

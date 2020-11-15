package com.beyond233.jwtlearn.annotation;

import com.beyond233.jwtlearn.constant.Role;

import java.lang.annotation.*;

/**
 * 描述: 表示在进行接口访问时要求用户拥有何种角色信息
 *
 * @author beyond233
 * @since 2020/11/15 10:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredRole {

    /**
     * 角色信息
     * */
    String value() default "";
}

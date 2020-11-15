package com.beyond233.jwtlearn.annotation;

import java.lang.annotation.*;

/**
 * 描述: 表示在访问接口时要求用户拥有何种权限
 *
 * @author beyond233
 * @since 2020/11/15 10:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {

    /**
     * 权限值
     * */
    String value() default "";
}

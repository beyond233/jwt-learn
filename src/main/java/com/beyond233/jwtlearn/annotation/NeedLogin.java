package com.beyond233.jwtlearn.annotation;

import java.lang.annotation.*;

/**
 * 描述: 表示在访问接口时需要用户先进行登录
 *
 * @author beyond233
 * @since 2020/11/15 0:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {
}

package com.beyond233.jwtlearn.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 描述: 登录校验条件
 *
 * @author beyond233
 * @since 2020/11/17 21:49
 */

@Slf4j
public class AuthEnableCondition implements Condition {

    /**
     * 当在application配置文件中配置auth.enable为true时，表示开启校验，即访问接口需要进行jwt校验
     * */
    private static final String AUTH_ENABLE_FALSE = "false";

    /**
     * 重写matches()方法： 满足条件返回true，否则false
     * */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        log.info("配置文件的值是： "+environment.getProperty("auth.enable"));
        return AUTH_ENABLE_FALSE.equals(environment.getProperty("auth.enable"));
    }
}

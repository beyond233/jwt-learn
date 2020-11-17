package com.beyond233.jwtlearn.config;

import com.beyond233.jwtlearn.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2020/11/17 21:59
 */
@Configuration
public class AuthEnableConfig {

    @Bean
    @Conditional(AuthEnableCondition.class)
    public User beyond() {
        return new User().setName("beyond");
    }


}

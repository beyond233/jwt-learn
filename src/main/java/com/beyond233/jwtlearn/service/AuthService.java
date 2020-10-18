package com.beyond233.jwtlearn.service;

import com.beyond233.jwtlearn.mapper.UserMapper;
import com.beyond233.jwtlearn.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2020/10/18 21:50
 */
@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    /**
     *  登录校验
     *
     * @param user .
     * @return  {@link boolean}
     * @author beyond233
     * @since 2020/10/18 21:52
     */
    public User loginAuth(User user) {
        return userMapper.loginUser(user);
    }
}

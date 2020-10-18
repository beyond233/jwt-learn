package com.beyond233.jwtlearn.controller;

import com.sun.org.apache.xpath.internal.objects.XString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 *  认证
 *
 * @author beyond233
 * */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * 模拟传统的session认证机制：
     *  用户信息认证成功后服务器会创建一个session并将用户信息存入session中（session是存
     *  储在内存中的）并将sessionID存入cookie中，然后将cookie下发给服务接口的调用者。下
     *  一次用户登陆时将携带这个cookie访问接口，此时服务器将从cookie中取出sessionID，然
     *  后通过sessionID从对应的session中取出此前保留的用户登陆的信息。
     *
     * */
    @GetMapping("/{username}")
    public String auth(@PathVariable String username, HttpServletRequest request) {
        // 模拟认证成功，服务器会将用户信息存入session中
        request.getSession().setAttribute("username",username);
        return "login success!";

    }
}

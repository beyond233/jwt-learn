package com.beyond233.jwtlearn.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beyond233.jwtlearn.constant.Constants;
import com.beyond233.jwtlearn.constant.Permission;
import com.beyond233.jwtlearn.constant.Role;
import com.beyond233.jwtlearn.pojo.User;
import com.beyond233.jwtlearn.service.AuthService;
import com.beyond233.jwtlearn.util.JWTUtil;
import com.beyond233.jwtlearn.util.Result;
import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 *  认证
 *
 * @author beyond233
 * */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

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

    /**
     * 登录
     * */
    @PostMapping("/login")
    public Result login(User user){
        User loginUser = authService.loginAuth(user);
        if (loginUser == null) {
            return Result.fail();
        }
        HashMap<String, String> payload = new HashMap<>();
        payload.put("userId", String.valueOf(loginUser.getId()));
        payload.put("username", loginUser.getName());
        payload.put(Constants.ROLE, Role.ADMIN.equals(loginUser.getName()) ? Role.ADMIN : Role.USER);
        payload.put(Constants.PERMISSION, Role.ADMIN.equals(loginUser.getName()) ? Permission.SUPREME : Permission.QUERY);
        String token = JWTUtil.generate(payload);
        return Result.success("login success",token);
    }

    /**
     * token校验
     * */
    @PostMapping("/token/verify")
    public Result tokenAuth(@RequestParam("token") String token) {
        if (token == null) {
            return Result.error("token不能为空", null);
        }
        try {
            DecodedJWT jwt = JWTUtil.decode(token);
            if (jwt == null) {
                return Result.fail("无效token!");
            }
            HashMap<String, String> map = new HashMap<>(2);
            map.put("userId", jwt.getClaim("userId").asString());
            map.put("username", jwt.getClaim("username").asString());
            return Result.success("login success",map);
        } catch (SignatureVerificationException e) {
            return Result.fail("无效签名！");
        }catch (TokenExpiredException e){
            return Result.fail("token已经过期！");
        }catch (AlgorithmMismatchException e){
            return Result.fail("token算法不一致！");
        }
    }

}

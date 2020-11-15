package com.beyond233.jwtlearn.controller;

import com.beyond233.jwtlearn.annotation.NeedLogin;
import com.beyond233.jwtlearn.annotation.NotNeedLogin;
import com.beyond233.jwtlearn.annotation.RequiredPermission;
import com.beyond233.jwtlearn.annotation.RequiredRole;
import com.beyond233.jwtlearn.constant.Permission;
import com.beyond233.jwtlearn.constant.Role;
import com.beyond233.jwtlearn.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述: 模拟业务Controller
 *
 * @author beyond233
 * @since 2020/11/15 0:24
 */
@RestController
@RequestMapping("/my")
public class BusinessController {

    @RequestMapping("/name")
    @NotNeedLogin
    public Result<String> nameInfo() {
        return Result.success("您的名字：徐可爱！");
    }

    @RequestMapping("/age")
    @NotNeedLogin
    public Result<String> ageInfo() {
        return Result.success("您的年龄：18！");
    }

    @RequestMapping("/password")
    @NeedLogin
    @RequiredRole(Role.ADMIN)
    public Result<String> resetPassword() {
        return Result.success("你的密码是：beyond");
    }


}

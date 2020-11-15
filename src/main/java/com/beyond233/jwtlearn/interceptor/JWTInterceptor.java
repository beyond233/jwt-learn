package com.beyond233.jwtlearn.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beyond233.jwtlearn.annotation.NotNeedLogin;
import com.beyond233.jwtlearn.annotation.RequiredPermission;
import com.beyond233.jwtlearn.annotation.RequiredRole;
import com.beyond233.jwtlearn.constant.Constants;
import com.beyond233.jwtlearn.constant.Role;
import com.beyond233.jwtlearn.controller.BusinessController;
import com.beyond233.jwtlearn.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.management.MethodInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 描述: jwt拦截器： 实现token的校验
 *
 * @author beyond233
 * @since 2020/10/18 22:48
 */
@Slf4j(topic = "JWT拦截器")
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 存放校验失败原因，响应给前端
        HashMap<String, String> resultMap = new HashMap<>(1);

        // 判断请求的接口方法是否需要进行登录（是否标注有@NotNeedLogin注解）
        String requestURI = request.getRequestURI();
        if ("".equals(requestURI) || requestURI == null) {
            throw new RuntimeException("请求uri无效！");
        }
        Class<?> clazz = Class.forName("com.beyond233.jwtlearn.controller.BusinessController");
        Method processMethod = null;
        for (Method method : clazz.getMethods()) {
            processMethod = method;
            String[] controllerUriPrefixArr = clazz.getAnnotation(RequestMapping.class).value();
            String controllerUriPrefix = controllerUriPrefixArr.length > 0 ? controllerUriPrefixArr[0] : "";
            String[] methodUriPrefixArr = method.getAnnotation(RequestMapping.class).value();
            String methodUriPrefix = methodUriPrefixArr.length > 0 ? methodUriPrefixArr[0] : "";
            String uri = controllerUriPrefix + methodUriPrefix;
            if (requestURI.equals(uri)) {
                if (method.isAnnotationPresent(NotNeedLogin.class)) {
                    return true;
                } else {
                    break;
                }
            }


        }

        // 从请求头中获取token
        String token = request.getHeader("token");

        if ("".equals(token) || token == null) {
            resultMap.put("msg", "请您先进行登录哦！");
            return failResponse(resultMap, response);
        }
        try {
            // 解析token
            DecodedJWT jwt = JWTUtil.decode(token);
            // 校验目标接口方法是否需要角色或权限信息
            boolean roleFlag;
            boolean permissionFlag;
            boolean isAdmin = false;
            assert processMethod != null;
            // 校验用户的角色和权限信息
            if (processMethod.isAnnotationPresent(RequiredRole.class)) {
                String userRole = jwt.getClaim(Constants.ROLE).asString();
                if (Role.ADMIN.equals(userRole)) {
                    return true;
                }else{
                    String requiredRole = processMethod.getAnnotation(RequiredRole.class).value();
                    roleFlag = requiredRole.equals(userRole);
                 }
            }else{
                roleFlag = true;
            }


            if (processMethod.isAnnotationPresent(RequiredPermission.class)) {
                String userPermission = jwt.getClaim(Constants.PERMISSION).asString();
                String requiredPermission = processMethod.getAnnotation(RequiredPermission.class).value();
                permissionFlag = requiredPermission.equals(userPermission);
            }else{
                permissionFlag = true;
            }

            if (roleFlag && permissionFlag) {
                return true;
            }
            resultMap.put("msg", "您没有对应的角色或权限！");
        } catch (SignatureVerificationException e) {
            resultMap.put("msg", "无效签名！");
        } catch (TokenExpiredException e) {
            resultMap.put("msg", "token已经过期！");
        } catch (AlgorithmMismatchException e) {
            resultMap.put("msg", "token算法不一致！");
        } catch (Exception e) {
            resultMap.put("msg", "无效token！");
        }
        // 校验失败：校验结果转换为json并响应给前端
        return failResponse(resultMap, response);
    }


    /**
     * 向前端返回失败信息
     * */
    public <T>boolean failResponse(T result, HttpServletResponse response) throws IOException {
        String responseInfo = new ObjectMapper().writeValueAsString(result);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(responseInfo);
        return false;
    }


}

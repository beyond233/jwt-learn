package com.beyond233.jwtlearn.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.beyond233.jwtlearn.util.JWTUtil;
import com.beyond233.jwtlearn.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 描述: jwt拦截器： 实现token的校验
 *
 * @author beyond233
 * @since 2020/10/18 22:48
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("token");
        // 存放校验失败原因，响应给前端
        HashMap<String, String> resultMap = new HashMap<>(1);

        try {
            // 校验通过，放行请求
            return JWTUtil.decode(token) != null;

        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            resultMap.put("msg", "无效签名！");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            resultMap.put("msg", "token已经过期！");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            resultMap.put("msg", "token算法不一致！");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", "无效token！");
        }
        // 校验失败：校验结果转换为json并响应给前端
        String responseInfo = new ObjectMapper().writeValueAsString(resultMap);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(responseInfo);

        return false;
    }
}

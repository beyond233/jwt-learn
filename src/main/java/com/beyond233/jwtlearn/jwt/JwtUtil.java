package com.beyond233.jwtlearn.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *  JWT工具
 * */
public class JwtUtil {

    /**
     *  token生成示例
     * */
    @Test
    public String tokenGenerate(){
        // header信息
        Map<String, Object> header = new HashMap<>();
        // token过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 20);

        // 生成token: Header为头信息；Claim为payload即携带的信息；ExpireAt为过期时间；sign为签名
        String token = JWT.create()
                .withHeader(header)
                .withClaim("userId", 233)
                .withClaim("username", "小徐")
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("@BEYOND!secret$?#"));

        System.err.println("生成的token："+token);

        return token;


    }
}

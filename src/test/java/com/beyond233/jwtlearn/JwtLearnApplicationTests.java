package com.beyond233.jwtlearn;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class JwtLearnApplicationTests {

    /**
     * jwt签名密钥
     * */
    private static final String SIGNATURE_KEY = "@BEYOND!secret$?#";

    /**
     *  token生成示例
     * */
    @Test
    public void tokenGenerate(){
        // header信息
        Map<String, Object> header = new HashMap<>();
        // token过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60);

        // 生成token: Header为头信息；Claim为payload即携带的信息；ExpireAt为过期时间；sign为签名
        String token = JWT.create()
                .withHeader(header)
                .withClaim("userId", 233)
                .withClaim("username", "小徐")
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGNATURE_KEY));

        System.err.println("生成的token：\n"+token);
    }

    /**
     * token验证
     * */
    @Test
    public void tokenVerify(){
        try {
            // 根据指定算法和密钥生成JWT验签器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SIGNATURE_KEY)).build();
            // 使用JWT验签器校验指定token，并得到解密后的token对象
            DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDMwMTUyNzAsInVzZXJJZCI6MjMzLCJ1c2VybmFtZSI6IuWwj-W-kCJ9.sxLMfFzGN3fwU6R_O8R3e_5kG-nfl0wOgd-FOHOFW4Y");

            // 从解密后的token中获取信息
            System.err.println(verify.getClaim("userId").asInt());
            System.err.println(verify.getClaim("username").asString());
            System.err.println("过期时间： "+verify.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            e.printStackTrace();
        }
    }

}

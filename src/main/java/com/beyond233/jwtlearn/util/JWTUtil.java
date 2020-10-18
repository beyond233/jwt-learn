package com.beyond233.jwtlearn.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  JWT工具
 *
 * @author BEYOND
 * */
@Slf4j(topic = "JWT")
public class JWTUtil {

    /**
     * JWT签名密钥
     * */
    private static final String SIGNATURE_KEY = "@BEYOND!secret$?#";

    /**
     * 生成token
     *
     * @param payloadMap 携带的信息
     * @return  {@link String} token字符串
     * @author beyond233
     * @since 2020/10/18 18:35
     */
    public static String generate(Map<String,String> payloadMap){
        // 创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        // 设置header信息
        Map<String, Object> header = new HashMap<>();
        // 设置token过期时间: 默认7天过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 7);
        Date expireTime = instance.getTime();
        // 设置payload
        payloadMap.forEach(builder::withClaim);
        // 设置加密算法(需指定密钥)
        Algorithm algorithm = Algorithm.HMAC256(SIGNATURE_KEY);

        // 生成token: Header为头信息；Claim为payload,即携带的信息；ExpireAt为过期时间；sign为签名
        String token = builder
                .withHeader(header)
                .withExpiresAt(expireTime)
                .sign(algorithm);

        log.info("生成token："+token);

        return token;
    }

    /**
     * token验签,返回验签结果
     *
     * @param tokenStr token字符串
     * @return  {@link boolean} true:验签通过;false:验签失败
     * @author beyond233
     * @since 2020/10/18 18:34
     */
    public static boolean verify(String tokenStr) {
        return decode(tokenStr) != null;
    }

    /**
     * 验签并返回验签成功后的token对象
     *
     * @param tokenStr token字符串
     * @return  {@link DecodedJWT}
     * @author beyond233
     * @since 2020/10/18 18:44
     */
    public static DecodedJWT decode(String tokenStr) {
        DecodedJWT decodedJWT;
        try {
            // 根据指定加密算法和密钥生成JWT验签器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SIGNATURE_KEY)).build();
            // 使用JWT验签器校验指定token，并得到解密后的token
            decodedJWT = jwtVerifier.verify(tokenStr);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            // 验签失败
            e.printStackTrace();
            throw e;
        }
        // 验签成功，返回解密后的token对象
        return decodedJWT;
    }
}

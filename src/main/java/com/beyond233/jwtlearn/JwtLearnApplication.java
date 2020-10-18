package com.beyond233.jwtlearn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *  JWT-Learn 启动类
 * */
@SpringBootApplication
@MapperScan("com.beyond233.jwtlearn.mapper")
public class JwtLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtLearnApplication.class, args);
    }

}

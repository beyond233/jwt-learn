package com.beyond233.jwtlearn;

import com.beyond233.jwtlearn.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticsTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        for (int i = 0; i < 24; i++) {
            System.out.println(i+" : "+userMapper.perHour(i));
        }
    }

    @Test
    public void test1() {
        System.out.println(userMapper.statistics());
    }

}

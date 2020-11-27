package com.beyond233.jwtlearn.mapper;

import com.beyond233.jwtlearn.pojo.User;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2020/10/18 21:44
 */
@Repository
public interface UserMapper {

    /**
     * 校验用户
     */
    @Select("select * from user where name=#{user.name} and password=#{user.password}")
    User loginUser(@Param("user") User user);

    /**
     * 统计每一个小时的人数
     * */
    @Select("SELECT HOUR(birth) AS hour,COUNT(*) AS count FROM user GROUP BY HOUR(birth)")
    List<Map<String,Integer>> statistics();

    @Select("select count(*) from user where hour(birth)=#{hour}")
    Integer perHour(Integer hour);
}



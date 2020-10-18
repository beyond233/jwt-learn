package com.beyond233.jwtlearn.mapper;

import com.beyond233.jwtlearn.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

}



package com.it.rword.mapper;

import com.it.rword.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据username查找用户数据
     * @param username 用户名
     * @return 返回值为用户数据
     */
    User findByUsername(String username);
}

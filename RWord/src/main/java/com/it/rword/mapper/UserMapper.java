package com.it.rword.mapper;

import com.it.rword.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface UserMapper {

    /**
     * 根据username查找用户数据
     * @param username 用户名
     * @return 返回值为用户数据
     */
    User findByUsername(String username);

    /**
     * 新增用户
     * @param user 用户数据
     * @return 返回值为受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据uid修改头像
     * @param uid 用户id
     * @param headPhoto 上传的头像
     * @return 返回值为受影响的行数
     */
    Integer updateHeadPhotoByUid(Integer uid, String headPhoto, String modifyPeople, Date modifyTime);

}

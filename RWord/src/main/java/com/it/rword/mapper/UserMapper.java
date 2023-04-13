package com.it.rword.mapper;

import com.it.rword.pojo.User;
import com.it.rword.pojo.Word;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

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

    /**
     * 根据uid修改密码
     * @param uid 用户id
     * @param password 密码
     * @return 返回值为收影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifyPeople, Date modifyTime);

    /**
     * 根据uid查询用户信息
     * @param uid 用户id
     * @return 返回值为用户数据
     */
    User findByUid(Integer uid);
}

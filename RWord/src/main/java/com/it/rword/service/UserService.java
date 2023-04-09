package com.it.rword.service;

import com.it.rword.pojo.User;

import java.util.Date;

public interface UserService {

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 用户密码
     * @return 当前登录的用户数据
     */
    User login(String username, String password);

    /**
     *用户注册功能
     * @param user 注册的新用户
     */
    void register(User user);

    void changeHeadPhoto(Integer uid, String headPhoto, String modifyPeople);
}

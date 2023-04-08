package com.it.rword.service;

import com.it.rword.pojo.User;

public interface UserService {

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 用户密码
     * @return 当前登录的用户数据
     */
    User login(String username, String password);

    /**
     * 用户注册功能
     * @param username 新用户名
     * @param password 新用户密码
     */
    //void sign(String username, String password);
}

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
     *用户注册功能
     * @param user 注册的新用户
     */
    void register(User user);

    /**
     * 修改头像
     * @param avatar 上传的头像
     */
    void changeAvatar(String avatar);


    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 修改后的新密码
     * @param username 用户名
     * @param uid 用户id
     * @return 修改的行数，因为是修改user表的密码所以正确修改的返回值为1
     */
    Integer changePassword(String oldPassword, String newPassword, String username, Integer uid);
}

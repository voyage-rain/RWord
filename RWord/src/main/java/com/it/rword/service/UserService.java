package com.it.rword.service;

import com.it.rword.pojo.User;

import java.util.Date;

public interface UserService {

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 当前登录的用户数据
     */
    User login(String username, String password);

    /**
     * 用户注册功能
     *
     * @param user 注册的新用户
     */
    void register(User user);

    /**
     * 修改头像
     *
     * @param uid          用户id
     * @param headPhoto    头像
     * @param modifyPeople 修改者
     */
    void changeHeadPhoto(Integer uid, String headPhoto, String modifyPeople);

    /**
     * 修改密码
     *
     * @param oldPassword 原密码
     * @param newPassword 修改后的新密码
     * @param username    用户名
     * @param uid         用户id
     * @return 修改的行数，因为是修改user表的密码所以正确修改的返回值为1
     */
    Integer changePassword(String oldPassword, String newPassword, String username, Integer uid);

    /**
     * 用户信息展示
     *
     * @param uid 用户id
     * @return 返回值为用户数据
     */
    User userInfo(Integer uid);

    /**
     * 发送验证码
     *
     * @param phone 电话号码
     * @return 返回值为验证码
     */
    String smsCode(String phone);

    /**
     * 忘记密码，通过电话号码修改密码word
     *
     * @param smsCodeConfirm  确认验证码
     * @param newPassword     新密码Password
     * @param confirmPassword 确认新密码
     * @param phone 电话号码
     */
    void forgetPassword(String smsCodeConfirm, String newPassword, String confirmPassword, String phone);
}

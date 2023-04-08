package com.it.rword.service.impl;

import com.it.rword.mapper.UserMapper;
import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 当前登录的用户数据
     */
    @Override
    public User login(String username, String password) {

        // 根据用户名查找该用户是否存在，如果不存在就返回null
        User result = userMapper.findByUsername(username);
        if(result == null){
            System.out.println("没有该用户！");
            return null;
        }

        // 得到数据库中的加密保存的密码
        String oldPassword = result.getPassword();

        // 获取改密码的加密盐值
        String salt = result.getSalt();
        // 按盐值来加密，加密算法为md5算法
        String newMd5Password = getMD5Password(password, salt);
        // 将从数据库取得的密码和用户输入的密码（加密后）进行比较
        if(newMd5Password.equals(oldPassword) ){
            return result;
        }
        return null;
    }

    /**
     * 定义一个md5算法的加密处理
     */
    private String getMD5Password(String password, String salt){
        for(int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}

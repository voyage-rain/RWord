package com.it.rword.service.impl;

import com.it.rword.mapper.UserMapper;
import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册功能
     *
     * @param user 注册的新用户
     */
    @Override
    public void register(User user) {
        // 得到用户注册输入的username
        String username = user.getUsername();
        // 判断该username是否被注册过
        User result = userMapper.findByUsername(username);

        // 如果result不为null说明该用户名已注册过
        if(result != null){
            System.out.println("该用户名已被注册过");
            return;
        }

        // 将输入的密码加密三次（与登录的加密次数对应，共用一个方法）
        String oldPassword = user.getPassword();
        // 生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 存入盐值
        user.setSalt(salt);
        // 将输入的密码用生成的盐值进行加密
        String md5Password = getMD5Password(oldPassword, salt);
        // 将生成的加密后的密码设置入用户中
        user.setPassword(md5Password);

        //补全数据
        // 设置用户状态
        user.setStatus(0);
        // 用户的头像后续上传，在这设置为null
        user.setHeadPhoto(null);
        // 该数据的创建者为该用户本人
        user.setCreator(user.getUsername());
        // 用户创建的时间（即当前时间）
        user.setCreateTime(new Date());
        // 没有更改的操作所以设置为null
        user.setModifyPeople(null);
        user.setModifyTime(null);


//         将该用户添加到数据库中
        Integer rows = userMapper.insert(user);
        if(rows != 1) {
            System.out.println("添加异常");
            return;
        }

        System.out.println("添加成功！");
    }

    /**
     * 修改头像
     *
     * @param avatar 上传的头像
     */
    @Override
    public void changeAvatar(String avatar) {

    }

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

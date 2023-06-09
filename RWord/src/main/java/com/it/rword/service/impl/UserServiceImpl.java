package com.it.rword.service.impl;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.it.rword.mapper.UserMapper;
import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import com.it.rword.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CodeUtil codeUtil;

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
     * 修改密码
     *
     * @param oldPassword 原密码
     * @param newPassword 修改后的新密码
     * @param username    用户名
     * @param uid         用户id
     * @return 修改的行数，因为是修改user表的密码所以正确修改的返回值为1
     */
    @Override
    public Integer changePassword(String oldPassword, String newPassword, String username, Integer uid) {

        // 通过uid来获取该用户的信息
        User user = userMapper.findByUid(uid);

        // 判断取得的用户是否是空
        if(user == null){
            System.out.println("该用户不存在");
            return 0;
        }

        // 判断取得的用户是否是该用户
        if(!user.getUsername().equals(username)){
            System.out.println("用户名和用户id不匹配");
            return -1;
        }

        // 加密输入的密码
        // 获取该用户密码的加密盐值
        String salt = user.getSalt();
        // 按盐值来加密，加密算法为md5算法
        String Md5OldPassword = getMD5Password(oldPassword, salt);

        // 获取该用户保存在数据库中的原密码
        String trueOldPassword = user.getPassword();

        // 匹配输入的原密码和保存在数据库中的密码是否相等
        if(!trueOldPassword.equals(Md5OldPassword)){
            System.out.println("用户输入的原密码错误");
            return -2;
        }

        // 按用户的盐值对新密码进行加密
        String Md5NewPassword = getMD5Password(newPassword, salt);

        // 将新修改的密码存入数据库中
        Integer result = userMapper.updatePasswordByUid(uid, Md5NewPassword, username, new Date());

        if(result != 1){
            System.out.println("存入新密码发生错误！");
            return -3;
        }

        return 1;
    }

    @Override
    public void changeHeadPhoto(Integer uid, String headPhoto, String modifyPeople) {
        // uid和modifiedPeople应从session作用域里获取
        userMapper.updateHeadPhotoByUid(uid, headPhoto, modifyPeople, new Date());
    }

    @Override
    public User userInfo(Integer uid) {
        User user = userMapper.findByUid(uid);
        if (user == null) {
            System.out.println("用户信息不存在！");
            return null;
        }
        user.setSalt(null);
        user.setCreator(null);
        user.setCreateTime(null);
        user.setModifyPeople(null);
        user.setModifyTime(null);
        return user;
    }

    @Override
    @CachePut(value = "smsCode", key = "#phone")
    public String smsCode(String phone) {
        // 设置参数
        int appId = 1400796698;
        String appKey = "cee3f7475248bfb778eba7e28e1439c1";
        int templateId = 1706934;
        String smsSign = "落叶几分秋公众号";

        // 生成随机的验证码
        String generator = codeUtil.generator(phone);

        String dest = "108";
        String min = "2";

        try{
            // 设置参数
            String[] params = {generator, dest, min};
            SmsSingleSender sender = new SmsSingleSender(appId, appKey);
//            SmsSingleSenderResult result = sender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
            sender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
        }catch (Exception e) {
            e.printStackTrace();
        }
        // 需要有返回值
        return generator;
    }

    /**
     * 忘记密码，通过电话号码修改密码word

     * @param smsCodeConfirm  确认验证码
     * @param newPassword     新密码Password
     * @param confirmPassword 确认新密码
     * @param phone 电话号码
     */
    @Override
    public void forgetPassword(String smsCodeConfirm, String newPassword, String confirmPassword, String phone) {
        User user = userMapper.findByPhone(phone);
        if (user == null) {
            System.out.println("用户数据不存在");
            return;
        }
        // 从内存中的取出验证码
        String cacheCode = codeUtil.get(phone);
        if (cacheCode == null) {
            // 验证码过期
            System.out.println("请重新获取验证码");
            return;
        } else if (!smsCodeConfirm.equals(cacheCode)) {
            // 将从内存中取出的验证码与传递过来的验证码比对
            System.out.println("验证码输入错误");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("密码确认错误");
            return;
        }
        String salt = user.getSalt();
        String Md5OldPassword = getMD5Password(newPassword, salt);
        Integer rows = userMapper.updatePasswordByPhone(phone, Md5OldPassword, phone, new Date());
        if (rows != 1) {
            System.out.println("密码设置失败");
        }
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

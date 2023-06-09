package com.it.rword.service;

import com.it.rword.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Resource
    private UserService userService;
    @Test
    public void testRegister(){
        User user = new User();
        user.setUsername("ran");
        user.setPassword("123");
        userService.register(user);
    }

    @Test
    public void testChangePassword(){
        userService.changePassword("123","321","ran",3);
    }

    @Test
    public void testLogin(){
        System.out.println(userService.login("ran","321"));
    }

    @Test
    public void userInfo() {
        System.out.println(userService.userInfo(3));
    }

    @Test
    public void smsCode() {
        userService.smsCode("15883031651");
    }

    @Test
    public void forgetPassword() {
//        userService.forgetPassword("123", "123", "123456", "123456", "15883031651");
    }
}

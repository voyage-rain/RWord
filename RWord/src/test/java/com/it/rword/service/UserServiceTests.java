package com.it.rword.service;

import com.it.rword.mapper.UserMapper;
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
        user.setUsername("haha");
        user.setPassword("123");
        userService.register(user);
    }
}

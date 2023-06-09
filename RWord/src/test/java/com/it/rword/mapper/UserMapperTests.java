package com.it.rword.mapper;

import com.it.rword.pojo.User;
import com.it.rword.pojo.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        if (rows == 1) {
            System.out.println("新增用户成功！");
        } else {
            System.out.println("新增用户失败！");
        }
    }

//    @Test
//    public void updateHeadPhotoByUid() {
//        userMapper.updateHeadPhotoByUid(1, "/upload/tom.png", "管理员", new Date());
//    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(2, "abc123", "管理员", new Date());
    }

    @Test
    public void findByUid() {
        User user = userMapper.findByUid(1);
        System.out.println(user);
    }

    @Test
    public void updatePasswordByPhone() {
        userMapper.updatePasswordByPhone("15883031651", "123456", "15883031651", new Date());
    }
}

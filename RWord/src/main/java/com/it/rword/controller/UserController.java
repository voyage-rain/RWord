package com.it.rword.controller;

import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import com.it.rword.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final int OK = 200;
    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public JsonResult<Void> register(User user) {
        userService.register(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User result = userService.login(username, password);
        if (result == null) {
            return new JsonResult<>(2);
        }
        session.setAttribute("uid", result.getUid());
        session.setAttribute("username", result.getUsername());
        return new JsonResult<>(OK, result);
    }

    @RequestMapping("/changePassword")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = (Integer)session.getAttribute("uid");
        String username = (String)session.getAttribute("username");
        Integer result = userService.changePassword(oldPassword, newPassword, username, uid);
        if(result == 0) {
            System.out.println("该用户不存在");
            return new JsonResult<>(0);
        }
        if(result == -1){
            System.out.println("用户名和用户id不匹配");
            return new JsonResult<>(-1);
        }
        if(result == -2){
            System.out.println("用户输入的原密码错误");
            return new JsonResult<>(-2);
        }
        if(result == -3){
            System.out.println("存入新密码发生错误！");
            return new JsonResult<>(-3);
        }
        return new JsonResult<>(OK);
    }
}

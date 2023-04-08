package com.it.rword.controller;

import com.it.rword.pojo.User;
import com.it.rword.service.UserService;
import com.it.rword.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public JsonResult<User> login(String username, String password) {
        User result = userService.login(username, password);
        if (result == null) {
            return new JsonResult<>(2);
        }
        return new JsonResult<>(OK, result);
    }
}

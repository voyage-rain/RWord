package com.it.rword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
    @RequestMapping("/index")
    public String home(){
        return "index";
    }
}

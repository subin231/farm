package com.farmstory.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("user/UserLogin")
    public String UserLogin(){
        return "user/UserLogin";
    }
}

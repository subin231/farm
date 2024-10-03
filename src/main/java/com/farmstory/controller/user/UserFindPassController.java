package com.farmstory.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFindPassController {

    @GetMapping("user/UserFindPass")
    public String UserFindPass(){
        return "user/UserFindPass";
    }
}

package com.farmstory.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserFindIdResultController {

    @GetMapping("user/UserFindIdResult")
    public String UserFindIdResult(){
        return "user/UserFindIdResult";
    }
}

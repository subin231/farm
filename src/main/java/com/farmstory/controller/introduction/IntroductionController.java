package com.farmstory.controller.introduction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroductionController {

    @GetMapping("/introduction/Introduction")
    public String introduction() {
        return "/introduction/Introduction";
    }
}

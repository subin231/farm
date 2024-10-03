package com.farmstory.controller.introduction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectionController {

    @GetMapping("/introduction/IntroDirection")
    public String IntroDirection() {
        return "/introduction/IntroDirection";
    }
}

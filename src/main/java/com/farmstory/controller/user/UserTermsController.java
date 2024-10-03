package com.farmstory.controller.user;

import com.farmstory.dto.TermsDTO;
import com.farmstory.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserTermsController {

    private final TermsService termsService;

    @GetMapping("user/UserTerms")
    public String UserUserTerms(Model model) {
        TermsDTO terms = termsService.selectTerms();
        model.addAttribute("terms", terms);

        return "user/UserTerms";
    }
}

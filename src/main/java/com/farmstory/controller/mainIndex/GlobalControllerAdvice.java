package com.farmstory.controller.mainIndex;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAuthenticationToModel(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 로그인한 사용자일 경우
            String auth = authentication.getAuthorities().toString();
            String role = auth.replace("[", "")        // 대괄호 제거
                    .replace("]", "")       // 대괄호 제거
                    .replace("ROLE_", "");  // ROLE_ 접두사 제거

            model.addAttribute("isAuthenticated", true);
            model.addAttribute("role", role);
            model.addAttribute("username", authentication.getName());
        } else {
            // 비로그인 사용자
            model.addAttribute("isAuthenticated", false);
        }
    }
}

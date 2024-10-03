package com.farmstory.controller.user;

import com.farmstory.dto.UserDTO;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserFindPassResultController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("user/UserFindPassResult")
    public String UserFindPassResult(HttpSession session, Model model) {
        session.setMaxInactiveInterval(60);
        String uid = (String) session.getAttribute("uid");
        if(uid == null) {
            return "redirect:/user/UserFindPass";
        }
        model.addAttribute("uid", uid);

        return "user/UserFindPassResult";
    }
    @PostMapping("user/UserFindPassResult")
    public String UserFindPassResult(UserDTO userDTO){

        String uid = userDTO.getUserUid();
        UserDTO resultUser = userService.selectUserById(uid);


        resultUser.setUserPass(userDTO.getUserPass());
        userService.updateUserPass(resultUser);

        return "redirect:/user/UserLogin";


    }
}

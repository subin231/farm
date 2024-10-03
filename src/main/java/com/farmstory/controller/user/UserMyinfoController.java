package com.farmstory.controller.user;

import com.farmstory.dto.UserDTO;
import com.farmstory.entity.User;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserMyinfoController {

    private final UserService userService;

    UserDTO resultUser = new UserDTO();

    @GetMapping("userInfo/UserMyinfo")
    public String UserMyinfo(Authentication authentication, Model model){

        if(authentication != null){
            String uid = authentication.getName();
            UserDTO userDTO = userService.selectUserById(uid);
            model.addAttribute("user", userDTO);

            resultUser = userDTO;
            return "user/UserMyinfo";
        }else{
            return "redirect:/user/UserLogin";
        }

    }
    @GetMapping("userInfo/UserMyinfo1")
    public String UserMyinfo1(){
        return "user/UserMyinfo1";
    }

    @ResponseBody
    @PostMapping("userInfo/UserMyinfo")
    public ResponseEntity UserMyinfo(@RequestBody UserDTO userDTO) {

        log.info(userDTO.toString());
        UserDTO resultUser = userService.selectUserById(userDTO.getUserUid());

        if(userDTO.getUserPass()!=null && resultUser!=null){
            resultUser.setUserPass(userDTO.getUserPass());
            ResponseEntity result = userService.updateUserPass(resultUser);

            return result;
        }else if(resultUser!=null){
            userDTO.setUserPass(resultUser.getUserPass());
            userDTO.setUserRegip(resultUser.getUserRegip());

            ResponseEntity result = userService.updateUser(userDTO);
            return result;
        }
        return ResponseEntity.ok().body(false);
    }

    @ResponseBody
    @PostMapping("userInfo/UserMyinfoLeave")
    public ResponseEntity UserMyinfoLeave(@RequestBody String uid) {

        try {
            if (uid != null) {
                ResponseEntity result = userService.leaveUser(uid);
                return result;
            } else {
                return ResponseEntity.ok().body(false);
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(false);
        }
    }

    @ResponseBody
    @PostMapping("userInfo/LeavePass")
    public ResponseEntity LeavePass(@RequestBody UserDTO userDTO) {
        String pass = userDTO.getUserPass();
        String uid = userDTO.getUserUid();

        UserDTO resultUser = userService.selectUserById(uid);
        ResponseEntity result = userService.MatchPass(resultUser, pass);

        return result;
    }

}

package com.farmstory.controller.user;

import com.farmstory.dto.UserDTO;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sound.midi.Receiver;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserFindIdController {

    private final UserService userService;

    @GetMapping("user/UserFindId")
    public String UserFindId(){
        return "user/UserFindId";
    }

    @PostMapping("user/UserFindId/{type}/{value}")
    public ResponseEntity UserFindId( HttpSession session,
                                      @PathVariable("type")  String type,
                                      @PathVariable("value") String value,
                                      @RequestBody UserDTO userDTO){

        String receivedEmail = userDTO.getUserEmail();
        String receivedName = userDTO.getUserName();
        String receivedUid = userDTO.getUserUid();

        UserDTO user = userService.selectUserForFindUser(type, value);

        try{

            if(user == null){
                return ResponseEntity.ok().body(false);
            }

            String name = user.getUserName();
            String email = user.getUserEmail();
            String uid = user.getUserUid();

            if(receivedName != null && receivedName.equals(name) && receivedEmail.equals(email)){
                userService.sendEmailCode(session, receivedEmail);
                user.setUserPass("");
                return ResponseEntity.ok().body(user);

            }else if( receivedUid != null && receivedUid.equals(uid) && receivedEmail.equals(email)){
                userService.sendEmailCode(session, receivedEmail);
                session.setAttribute("uid", user.getUserUid());
                return ResponseEntity.ok().body(user);
            }else{
                return ResponseEntity.ok().body(false);
            }

        }catch (Exception e){
            return ResponseEntity.ok().body(false);
        }
    }

}

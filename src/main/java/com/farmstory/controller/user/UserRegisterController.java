package com.farmstory.controller.user;

import com.farmstory.dto.UserDTO;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class UserRegisterController {

    private final UserService userService;

    @GetMapping("user/UserRegister")
    public String UserRegister(){
        return "user/UserRegister";
    }

    @PostMapping("user/UserRegister")
    public String UserRegister(UserDTO userDTO, HttpServletRequest req){
        String regip = req.getRemoteAddr();
        userDTO.setUserRegip(regip);
        userService.insertUser(userDTO);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("user/UserRegister/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type")  String type,
                                       @PathVariable("value") String value){

        log.info("type : " + type + ", value : " + value);

        int count = userService.selectCountUser(type, value);
        log.info("count : " + count);

        // 중복 없으면 이메일 인증코드 발송
        if(count == 0 && type.equals("email")){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }


    // 이메일 인증 코드 검사
    @ResponseBody
    @PostMapping("user/UserRegister/email")
    public ResponseEntity<?> checkEmail(HttpSession session, @RequestBody Map<String, String> jsonData){

        log.info("checkEmail code : " + jsonData);

        String receiveCode = jsonData.get("code");
        log.info("checkEmail receiveCode : " + receiveCode);

        String sessionCode = (String) session.getAttribute("code");

        if(sessionCode.equals(receiveCode)){
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", true);

            return ResponseEntity.ok().body(resultMap);
        }else{
            // Json 생성
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("result", false);

            return ResponseEntity.ok().body(resultMap);
        }
    }

}

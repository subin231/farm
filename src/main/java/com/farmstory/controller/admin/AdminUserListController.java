package com.farmstory.controller.admin;

import com.farmstory.dto.OrderDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.entity.User;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminUserListController {

    private final UserService userService;

    @GetMapping("/admin/UserList")
    public String AdminUser(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<UserDTO> userDto = userService.selectUserAll(pageRequestDTO);
        log.info(userDto);
        model.addAttribute("userDtos", userDto);
        return "/admin/user/UserList";
    }
    @GetMapping("/admin/UserList/{userUid}")
    @ResponseBody
    public ResponseEntity<UserDTO> getOrderDetail(@PathVariable String userUid) {
        UserDTO userDetail = userService.selectUserById(userUid);
        log.info("userDetail" + userDetail);
        if (userDetail != null) {
            return ResponseEntity.ok(userDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/UserList/Update")
    public ResponseEntity<Map<String, Object>> updateUserGrade(@RequestBody UserDTO userDto) {
        // 로그 찍기
        log.info("Received User ID: " + userDto.getUserUid());
        log.info("Received Grade: " + userDto.getUserRole());

        Map<String, Object> response = new HashMap<>();
        try {
            // 유저 등급 업데이트 로직 호출
            boolean isUpdated = userService.updateUserGrade(userDto.getUserUid(), userDto.getUserRole());

            if (isUpdated) {
                response.put("result", 1);  // 성공 응답
                response.put("message", "User grade updated successfully.");
            } else {
                response.put("result", 0);  // 실패 응답
                response.put("message", "Failed to update user grade.");
            }
            return ResponseEntity.ok(response);  // JSON 응답

        } catch (Exception e) {
            log.error("Error updating user grade", e);
            response.put("result", 0);
            response.put("message", "Error occurred while updating user grade.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
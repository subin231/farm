package com.farmstory.service;

import com.farmstory.dto.UserDTO;
import com.farmstory.dto.pageDTO.PageRequestDTO;
import com.farmstory.dto.pageDTO.PageResponseDTO;
import com.farmstory.entity.User;
import com.farmstory.repository.user.UserRepository;
import com.querydsl.core.Tuple;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Log4j2
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final ModelMapper modelMapper;


    //유저 정보 저장
    public void insertUser(UserDTO userDTO) {

        String encoded = passwordEncoder.encode(userDTO.getUserPass());
        userDTO.setUserPass(encoded);
        User entity = userDTO.toEntity();
        userRepository.save(entity);
    }



    //유저 정보 전체 가져오기
    public List<UserDTO> selectUsers(){

        List<User> userall = userRepository.findAll();

        List<UserDTO> users = userall
                .stream()
                .map(entity -> entity.toDTO())
                .collect(Collectors.toList());
        for(UserDTO user : users) {
            LocalDateTime userDateTime = user.getUserRegDate();
            if(userDateTime != null) {
                String fullDateTime = userDateTime.toString();
                String[] split = fullDateTime.split("T");
                if(split.length == 2) {
                    user.setDate(split[0]);
                    user.setTimeDate(split[1]);
                }
            }
        }
        return users;
    }
    //원하는 유저
    public UserDTO selectUserById(String uid) {
        Optional<User> opt = userRepository.findById(uid);
        if(opt.isPresent()) {
            User user = opt.get();
            return user.toDTO();
        }
        return null;
    }
    public ResponseEntity MatchPass(UserDTO userDTO, String pass) {
        String resultPass = userDTO.getUserPass();

        if(passwordEncoder.matches(pass, resultPass)) {
            return ResponseEntity.ok().body(true);
        }else{
            return ResponseEntity.ok().body(false);
        }
    }

    //페이지 형식으로 유저 정보 가져오기
    public PageResponseDTO<UserDTO> selectUserAll (PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("userUid",false);
        Page<Tuple> pageOrder = userRepository.selectUserAllForList(pageRequestDTO, pageable);
        List<UserDTO> userList = pageOrder.getContent().stream().map(tuple -> {
            User user = tuple.get(0, User.class);
            return modelMapper.map(user, UserDTO.class);
        }).toList();
        for(UserDTO user : userList) {
            LocalDateTime userDateTime = user.getUserRegDate();
            if(userDateTime != null) {
                String fullDateTime = userDateTime.toString();
                String[] split = fullDateTime.split("T");
                if(split.length == 2) {
                    user.setDate(split[0]);
                    user.setTimeDate(split[1]);
                }
            }
        }
        int total = (int) pageOrder.getTotalElements();
        return PageResponseDTO.<UserDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(userList)
                .total(total)
                .build();
    }

    public UserDTO selectUserForFindUser(String type, String value) {

        User user = null;

        if(type.equals("uid")){
            user = userRepository.findByUserUid(value);
        }else if(type.equals("email")){
            user = userRepository.findByUserEmail(value);
        }
        return modelMapper.map(user, UserDTO.class);
    }

    public ResponseEntity updateUser(UserDTO userDTO) {
        if(userDTO.getUserUid() != null) {
            User entity = modelMapper.map(userDTO, User.class);
            userRepository.save(entity);

            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }

    public ResponseEntity updateUserPass(UserDTO userDTO) {

        if(userDTO.getUserName() != null && userDTO.getUserPass() != null) {
            String encoded = passwordEncoder.encode(userDTO.getUserPass());
            userDTO.setUserPass(encoded);

            User entity = modelMapper.map(userDTO, User.class);
            userRepository.save(entity);

            return ResponseEntity.ok().body(true);
        }else{
            return ResponseEntity.ok().body(false);
        }

    }

    public int selectCountUser(String type, String value){

        int count = 0;

        if(type.equals("uid")){
            count = userRepository.countByUserUid(value);
        }else if(type.equals("nick")){
            count = userRepository.countByUserNick(value);
        }else if(type.equals("hp")){
            count = userRepository.countByUserHp(value);
        }else if(type.equals("email")){
            count = userRepository.countByUserEmail(value);
        }
        return count;
    }

    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmailCode(HttpSession session, String receiver){


        // MimeMessage 생성
        MimeMessage message = mailSender.createMimeMessage();

        // 인증코드 생성 후 세션 저장
        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        session.setAttribute("code", String.valueOf(code));


        String title = "sboard 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.</h1>";

        try {
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            // 메일 발송
            mailSender.send(message);
        }catch(Exception e){
            log.error("sendEmailConde : " + e.getMessage());
        }
    }

    //선택한 유저 정보 삭제
    public void deleteUserById(String uid) {
        //Entity 삭제 (데이터베이스 Delete)
        userRepository.deleteById(uid);
    }

    @Transactional
    public ResponseEntity leaveUser(String uid) {


        User user = userRepository.findByUserUid(uid);
        LocalDateTime regDate = user.getUserRegDate();
        LocalDateTime leaveDateTime = LocalDateTime.now();

        UserDTO userDTO = new UserDTO();
        userDTO.setUserUid(uid);
        userDTO.setUserRegDate(regDate);
        userDTO.setUserLeaveDate(leaveDateTime);

        User entity = modelMapper.map(userDTO, User.class);
        userRepository.save(entity);

        return ResponseEntity.ok().body(true);
    }

    //유저 등급 수정
    public boolean updateUserGrade(String userUid, String newGrade) {
        // 유저 ID로 유저 찾기
        Optional<User> optionalUser = userRepository.findById(userUid);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserRole(newGrade);  // 유저 등급 업데이트
            userRepository.save(user);  // 데이터베이스에 저장
            return true;  // 성공 시 true 반환
        } else {
            return false;  // 유저가 없을 경우 false 반환
        }
    }
}
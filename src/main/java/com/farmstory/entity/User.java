package com.farmstory.entity;


import com.farmstory.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "user")
public class User {
    @Id
    private String userUid;
    private String userPass;
    private String userName;
    private String userNick;
    private String userEmail;
    private String userHp;
    private String userZip;
    private String userAddr1;
    private String userAddr2;
    private String userRegip;
    private String userRole;

    @CreationTimestamp
    private LocalDateTime userRegDate;
    private LocalDateTime userLeaveDate; // 탈퇴 버튼이 후 떠나는 일자를 확정가능
    private int userTotalPoint;

    public UserDTO toDTO() {
        return UserDTO.builder()
                .userUid(userUid)
                .userPass(userPass)
                .userName(userName)
                .userNick(userNick)
                .userEmail(userEmail)
                .userHp(userHp)
                .userZip(userZip)
                .userAddr1(userAddr1)
                .userAddr2(userAddr2)
                .userRegip(userRegip)
                .userRole(userRole)
                .userRegDate(userRegDate)
                .userTotalPoint(userTotalPoint)
                .build();
    }
}

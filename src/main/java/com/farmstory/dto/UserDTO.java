package com.farmstory.dto;

import com.farmstory.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
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

    @Builder.Default
    private String userRole = "USER";


    private LocalDateTime userRegDate;
    private LocalDateTime userLeaveDate; // 탈퇴 버튼이 후 떠나는 일자를 확정가능
    private String Date;
    private String timeDate;
    private int userTotalPoint;


    public User toEntity() {
        return User.builder()
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
                .userLeaveDate(userLeaveDate)
                .userTotalPoint(userTotalPoint)
                .build();
    }

}

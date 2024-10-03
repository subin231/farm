package com.farmstory.entity;

import com.farmstory.dto.PointDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "points")
public class Point {
    @Id
    private int pointNo;
    private String userId;
    private int pointValue;

    @CreationTimestamp
    private LocalDateTime pointDate;

    public PointDTO toDTO() {
        return PointDTO.builder()
                .pointNo(pointNo)
                .userId(userId)
                .pointValue(pointValue)
                .pointDate(pointDate)
                .build();
    }

}

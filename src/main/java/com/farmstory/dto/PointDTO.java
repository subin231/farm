package com.farmstory.dto;

import com.farmstory.entity.Point;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDTO {
    private int pointNo;
    private String userId;
    private int pointValue;
    @CreationTimestamp
    private LocalDateTime pointDate;


    public Point toEntity() {
        return Point.builder()
                .pointNo(pointNo)
                .userId(userId)
                .pointValue(pointValue)
                .pointDate(pointDate)
                .build();
    }

}

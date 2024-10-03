package com.farmstory.dto;

import com.farmstory.entity.Event;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String title;
    private String content;

    private String Start_date;
    private String End_date;

    public Event toEntity(){
        return Event.builder()
                .no(this.no)
                .title(this.title)
                .content(this.content)
                .Start_date(this.Start_date)
                .End_date(this.End_date)
                .build();
    }

}

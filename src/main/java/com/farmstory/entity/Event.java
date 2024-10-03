package com.farmstory.entity;

import com.farmstory.dto.EventDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private String title;
    private String content;

    private String Start_date;
    private String End_date;

    public EventDTO toDTO(){
        return EventDTO.builder()
                .no(this.no)
                .title(this.title)
                .content(this.content)
                .Start_date(this.Start_date)
                .End_date(this.End_date)
                .build();
    }
}


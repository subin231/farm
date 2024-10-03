package com.farmstory.entity;

import com.farmstory.dto.TermsDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "terms")
public class Terms {

    @Id
    private int termsNo;
    private String terms;
    private String privacy;

    public TermsDTO toDTO() {
        return TermsDTO.builder()
                .termsNo(termsNo)
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}

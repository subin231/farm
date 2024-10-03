package com.farmstory.entity;

import com.farmstory.dto.CartDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티 객체 정의
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;
    private String userId;
    private int prodNo;
    private int cartProdCount;

    @CreationTimestamp
    private String cartProdDate;

    public CartDTO toDTO() {
        return CartDTO.builder()
                .cartNo(cartNo)
                .userId(userId)
                .prodNo(prodNo)
                .cartProdCount(cartProdCount)
                .cartProdDate(cartProdDate)
                .build();
    }
}

package com.farmstory.dto;

import com.farmstory.entity.Cart;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private int cartNo;
    private String userId;
    private int prodNo;
    private int cartProdCount;
    @CreationTimestamp
    private String cartProdDate;

    private ProductDTO prodDTO;

    public Cart toEntity() {
        return Cart.builder()
                .cartNo(cartNo)
                .userId(userId)
                .prodNo(prodNo)
                .cartProdCount(cartProdCount)
                .cartProdDate(cartProdDate)
                .build();
    }
}

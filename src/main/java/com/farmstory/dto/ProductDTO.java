package com.farmstory.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private int prodNo;
    private int prodCateType;
    private String prodName;
    private int prodPrice;
    private int prodStock;
    private int prodDiscount;
    private String prodImage1;
    private String prodImage2;
    private String prodImage3;
    private MultipartFile prodImageName1;
    private MultipartFile prodImageName2;
    private MultipartFile prodImageName3;
    private int prodPoint;
    private String prodEtc;
    private int prodDelivery;

    @CreationTimestamp
    private LocalDateTime prodRegDate;
    private String Date;
    private String timeDate;
    // 추가 필드
    private int cartProdCount;

    public ProductDTO toEntity() {
        return ProductDTO.builder()
                .prodNo(prodNo)
                .prodCateType(prodCateType)
                .prodName(prodName)
                .prodPrice(prodPrice)
                .prodStock(prodStock)
                .prodDiscount(prodDiscount)
                .prodImage1(prodImage1)
                .prodImage2(prodImage2)
                .prodImage3(prodImage3)
                .prodPoint(prodPoint)
                .prodEtc(prodEtc)
                .prodDelivery(prodDelivery)
                .prodRegDate(prodRegDate)
                .build();
    }

}

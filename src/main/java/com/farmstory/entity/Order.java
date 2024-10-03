package com.farmstory.entity;

import com.farmstory.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity                 // 엔티티
// 객체 정의
@Table(name = "`orders`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderuserUid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading to improve performance
    @JoinColumn(name = "prodNo") // FK reference
    private Product product;

    private String orderAddr;
    private int orderItemDiscount;

    @CreationTimestamp
    private LocalDateTime orderDate;
    private String orderReceiveName;
    private String orderReceiveHp;
    private String orderMemo;
    private String orderSendHp;
    private String orderSendName;

    private int orderPrice;
    private int orderStock;
    private int orderDeliveryFee;
    private int orderDiscount;
    private int ordersGroup;
    private int orderTotalPrice;
    private int orderUsePoint;
    private int orderPlusPoint;
    private int orderPay;

    public OrderDTO toDTO(){
        return OrderDTO.builder()
                .orderNo(orderNo)
                .orderAddr(orderAddr)
                .orderItemDiscount(orderItemDiscount)
                .orderDate(orderDate)
                .orderReceiveName(orderReceiveName)
                .orderReceiveHp(orderReceiveHp)
                .orderMemo(orderMemo)
                .orderSendHp(orderSendHp)
                .orderSendName(orderSendName)
                .orderPrice(orderPrice)
                .orderStock(orderStock)
                .orderDeliveryFee(orderDeliveryFee)
                .orderDiscount(orderDiscount)
                .ordersGroup(ordersGroup)
                .orderTotalPrice(orderTotalPrice)
                .orderUsePoint(orderUsePoint)
                .orderPlusPoint(orderPlusPoint)
                .orderPay(orderPay)
                .build();
    }

}
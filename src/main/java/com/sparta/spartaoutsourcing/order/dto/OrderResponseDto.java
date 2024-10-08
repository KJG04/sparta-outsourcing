package com.sparta.spartaoutsourcing.order.dto;


import com.sparta.spartaoutsourcing.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private final String email;
    private final String storeName;
    private final String menuName;
    private final int quantity;
    private final Long totalPrice;
    private final String state;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public OrderResponseDto(Order order) {
        this.email = order.getUser().getEmail();
        this.storeName = order.getStore().getStoreName();
        this.menuName = order.getMenu().getMenuName();
        this.quantity = order.getQuantity();
        this.totalPrice = (long) order.getQuantity() * order.getMenu().getPrice();
        this.state = order.getState().getStateName();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
    }
}

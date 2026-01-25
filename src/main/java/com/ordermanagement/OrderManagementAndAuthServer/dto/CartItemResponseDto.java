package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class CartItemResponseDto {

    private Long id;
    private Long productId;
    private int quantity;
    private double price;

    public CartItemResponseDto(Long id, Long productId, int quantity,double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price=price;
    }
}

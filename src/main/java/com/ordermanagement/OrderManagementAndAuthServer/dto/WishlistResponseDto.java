package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class WishlistResponseDto {

    private Long id;
    private Long productId;

    public WishlistResponseDto(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }
}

package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {

    private Long id;
    private Long userId;
    private Long productId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}

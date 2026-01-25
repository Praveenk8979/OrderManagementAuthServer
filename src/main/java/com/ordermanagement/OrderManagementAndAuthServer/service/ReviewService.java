package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.ReviewResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(Review review);
    List<Review> getProductReviews(Long productId);

    List<ReviewResponseDto> getAllReviews();

    void deleteReview(Long id);
}

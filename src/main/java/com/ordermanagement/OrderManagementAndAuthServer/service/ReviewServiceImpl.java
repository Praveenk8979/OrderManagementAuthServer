package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.ReviewResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.Review;
import com.ordermanagement.OrderManagementAndAuthServer.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepo repo;
    @Override
    public Review addReview(Review review) {
        if(review.getRating() < 1 || review.getRating() > 5){
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        return repo.save(review);
    }

    @Override
    public List<Review> getProductReviews(Long productId) {
        return repo.findByProductId(productId);
    }

    @Override
    public List<ReviewResponseDto> getAllReviews() {
        return repo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deleteReview(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Review not found with id: " + id);
        }
        repo.deleteById(id);
    }

    private ReviewResponseDto mapToDto(Review r) {
    ReviewResponseDto dto = new ReviewResponseDto();
    dto.setId(r.getId());
    dto.setUserId(r.getUserId());
    dto.setProductId(r.getProductId());
    dto.setRating(r.getRating());
    dto.setComment(r.getComment());
    dto.setCreatedAt(r.getCreateAt());
    return dto;
}
}



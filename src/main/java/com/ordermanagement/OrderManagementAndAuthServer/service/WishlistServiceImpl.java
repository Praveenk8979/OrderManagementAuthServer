package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.WishlistResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.CartItem;
import com.ordermanagement.OrderManagementAndAuthServer.model.WishlistItem;
import com.ordermanagement.OrderManagementAndAuthServer.repo.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    private WishlistRepo wishlistRepo;
    @Override
    public WishlistItem addWishlist(WishlistItem item) {
        return wishlistRepo.save(item);
    }

    @Override
    public PageResponseDto<WishlistResponseDto> getUserWishlist(Long userId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishlistItem> pageData = wishlistRepo.findByUserId(userId, pageable);

        List<WishlistResponseDto> dtoList = pageData.getContent()
                .stream()
                .map(w -> new WishlistResponseDto(w.getId(), w.getProductId()))
                .toList();

        return new PageResponseDto<>(
                dtoList, page, size,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }

    @Override
    public PageResponseDto<WishlistResponseDto> getAllWishlistItems(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishlistItem> pageData = wishlistRepo.findAll(pageable);

        List<WishlistResponseDto> dtoList = pageData.getContent()
                .stream()
                .map(w -> new WishlistResponseDto(w.getId(), w.getProductId()))
                .toList();

        return new PageResponseDto<>(
                dtoList, page, size,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }


    @Override
    public void removeWishlist(Long itemId) {
        if(!wishlistRepo.existsById(itemId)) {
            throw new RuntimeException("Wishlist item not found");
        }
        wishlistRepo.deleteById(itemId);
    }
}

package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.WishlistResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.WishlistItem;

import java.util.List;

public interface WishlistService {

    WishlistItem addWishlist(WishlistItem item);

    PageResponseDto<WishlistResponseDto> getUserWishlist(Long userId, int page, int size);

    PageResponseDto<WishlistResponseDto> getAllWishlistItems(int page, int size);

    void removeWishlist(Long itemId);
}

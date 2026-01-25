package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.CartItemResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.CartItem;

import java.util.List;

public interface CartService {

    CartItem addToCart(CartItem item);
    PageResponseDto<CartItemResponseDto> getUserCart(Long userId, int page, int size);

    PageResponseDto<CartItemResponseDto> getAllCarts(int page, int size);
    CartItem updateCart(CartItem item);
    void removeFromCart(Long itemId);
}

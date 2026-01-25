package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.CartItemResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.CartItem;
import com.ordermanagement.OrderManagementAndAuthServer.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepo cartRepo;
    @Override
    public CartItem addToCart(CartItem item) {
        if(item.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        return cartRepo.save(item);
    }

    @Override
    public PageResponseDto<CartItemResponseDto> getUserCart(Long userId,int page , int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<CartItem> pageData = cartRepo.findByUserId(userId, pageable);

        List<CartItemResponseDto> dtoList = pageData.getContent()
                .stream()
                .map(c -> new CartItemResponseDto(
                        c.getId(),
                        c.getProductId(),
                        c.getQuantity(),
                        c.getPrice()
                ))
                .toList();

        return new PageResponseDto<>(
                dtoList,
                page,
                size,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

    }

    @Override
    public PageResponseDto<CartItemResponseDto> getAllCarts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CartItem> pageData = cartRepo.findAll(pageable);

        List<CartItemResponseDto> dtoList = pageData.getContent()
                .stream()
                .map(c -> new CartItemResponseDto(
                        c.getId(),
                        c.getProductId(),
                        c.getQuantity(),
                        c.getPrice()
                ))
                .toList();

        return new PageResponseDto<>(
                dtoList,
                page,
                size,
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }

    @Override
    public CartItem updateCart(CartItem item) {
        CartItem existing = cartRepo.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        existing.setQuantity(item.getQuantity());
        existing.setPrice(item.getPrice());
        return cartRepo.save(existing);
    }

    @Override
    public void removeFromCart(Long itemId) {
        if(!cartRepo.existsById(itemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartRepo.deleteById(itemId);

    }
}

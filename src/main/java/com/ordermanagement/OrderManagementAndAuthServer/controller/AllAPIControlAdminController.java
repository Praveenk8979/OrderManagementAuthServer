package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.*;
import com.ordermanagement.OrderManagementAndAuthServer.model.*;
import com.ordermanagement.OrderManagementAndAuthServer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlAdmin")
public class AllAPIControlAdminController {

    @Autowired
    private CartService cartService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private NotificationService notificationService;

    // ================= CART =================
    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('CART_READ_ALL')")
    public PageResponseDto<CartItemResponseDto> allCarts(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size
    ){

        return cartService.getAllCarts(page,size);
    }

    //does'nt show mssg delete
    @DeleteMapping("/cart/{id}")
    public void deleteCart(@PathVariable Long id){
        cartService.removeFromCart(id);
    }

    // ================= WISHLIST =================
    @GetMapping("/wishlist")
    @PreAuthorize("hasAuthority('WISHLIST_MANAGE_ALL')")
    public PageResponseDto<WishlistResponseDto> allWishlist(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size
    ){
        return wishlistService.getAllWishlistItems(page,size);
    }

    // ================= ADDRESS =================
    @GetMapping("/address")
    @PreAuthorize("hasAuthority('ADDRESS_READ_ALL')")
    public ApiResponse<PageResponseDto<AddressResponseDto>> allAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ApiResponse.success(
                "All addresses fetched",
                addressService.getAllAddresses(page, size)
        );
    }

    // ================= REVIEW =================
    @GetMapping("/review")
    @PreAuthorize("hasAuthority('REVIEW_MANAGE_ALL')")
    public ApiResponse<List<ReviewResponseDto>> reviews(){
        return ApiResponse.success(
                "All reviews fetched",
                reviewService.getAllReviews()
        );
    }

    //does'nt show mssg delete
    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
    }

    // ================= NOTIFICATION =================
    @PostMapping("/notification")
    @PreAuthorize("hasAuthority('NOTIFICATION_SEND')")
    public Notifications send(@RequestBody Notifications notification){
        return notificationService.sendNotification(notification);
    }
}

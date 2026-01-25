package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.CartItemResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.PageResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.WishlistResponseDto;
import com.ordermanagement.OrderManagementAndAuthServer.model.*;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import com.ordermanagement.OrderManagementAndAuthServer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlUsers")
public class AllAPIControlUserController {

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
    @Autowired
    private UserRepo userRepo;

    //  Helper
    private User getUser(Authentication auth){
        return userRepo.findByUserName(auth.getName()).orElseThrow();
    }

    // ================= CART =================
    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('CART_READ')")
    public PageResponseDto<CartItemResponseDto> getCart(Authentication auth,
                                                      @RequestParam(defaultValue = "0")   int page,
                                                       @RequestParam(defaultValue = "10") int size ){

        return cartService.getUserCart(getUser(auth).getId(),page,size);
    }

    @PostMapping("/cart")
    @PreAuthorize("hasAuthority('CART_UPDATE')")
    public CartItem addCart(@RequestBody CartItem item, Authentication auth){
        item.setUserId(getUser(auth).getId());
        return cartService.addToCart(item);
    }

    @DeleteMapping("/cart/{id}")
    @PreAuthorize("hasAuthority('CART_UPDATE')")
    public String deleteCart(@PathVariable Long id){
        cartService.removeFromCart(id);
        return "Cart item removed";
    }

    // ================= WISHLIST =================
    @GetMapping("/wishlist")
    @PreAuthorize("hasAuthority('WISHLIST_MANAGE')")
    public PageResponseDto<WishlistResponseDto> wishlist(Authentication auth,
                                                         @RequestParam(defaultValue = "0") int page ,
                                                         @RequestParam(defaultValue = "10") int size){
        return wishlistService.getUserWishlist(getUser(auth).getId(),page,size);
    }

    @PostMapping("/wishlist")
    @PreAuthorize("hasAuthority('WISHLIST_MANAGE')")
    public WishlistItem addWishlist(@RequestBody WishlistItem item, Authentication auth){
        item.setUserId(getUser(auth).getId());
        return wishlistService.addWishlist(item);
    }

    @DeleteMapping("/wishlist/{id}")
    @PreAuthorize("hasAuthority('WISHLIST_MANAGE')")
    public String removeWishlist(@PathVariable Long id){
        wishlistService.removeWishlist(id);
        return "Wishlist item removed";
    }

    // ================= ADDRESS =================
    @GetMapping("/address")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public List<Address> addresses(Authentication auth){
        return addressService.getUserAddresses(getUser(auth).getId());
    }

    @PostMapping("/address")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public Address addAddress(@RequestBody Address address, Authentication auth){
        address.setUserId(getUser(auth).getId());
        return addressService.addAddress(address);
    }

    @DeleteMapping("/address/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public String deleteAddress(@PathVariable Long id){
        addressService.removeAddress(id);
        return "Address deleted";
    }

    // ================= REVIEW =================
    @PostMapping("/review")
    @PreAuthorize("hasAuthority('REVIEW_MANAGE')")
    public Review addReview(@RequestBody Review review){
        return reviewService.addReview(review);
    }

    @GetMapping("/review/{productId}")
    public List<Review> getReviews(@PathVariable Long productId){
        return reviewService.getProductReviews(productId);
    }

    // ================= NOTIFICATION =================
    @GetMapping("/notifications")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ')")
    public List<Notifications> notifications(Authentication auth){
        return notificationService.getUserNotifications(getUser(auth).getId());
    }
}

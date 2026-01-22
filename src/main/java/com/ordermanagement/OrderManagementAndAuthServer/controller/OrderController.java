package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.model.Order;
import com.ordermanagement.OrderManagementAndAuthServer.model.OrderItem;
import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import com.ordermanagement.OrderManagementAndAuthServer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ORDER_CREATE')")
    @PostMapping
    public Order createOrder(@RequestBody List<OrderItem> items, Authentication authentication){

        //Username from jwt
        String userName=authentication.getName();

        //fetch user from db
        User user=userRepo.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException("User not found!.."));


        return orderService.createOrder(user.getId(),items);
    }

    @PreAuthorize("hasAuthority('ORDER_READ_SELF')")
    @GetMapping
    public List<Order> getUserOrders(Authentication auth) {
        String userName=auth.getName();

        User user=userRepo.findByUserName(userName)
                .orElseThrow(()-> new RuntimeException("User not found!.."));

        return orderService.getUserOrders(user.getId());
    }

    @PreAuthorize("hasAuthority('ORDER_READ_SELF')")
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id, Authentication auth) {

        Order order=orderService.getOrderById(id);
        String userName=auth.getName();
        User user=userRepo.findByUserName(userName).orElseThrow();

        //Ensure user owns this order
        if(!order.getUserId().equals(user.getId())){
            throw new RuntimeException("Access Denied!..");
        }

        return order;
        //return orderService.getOrderById(id);
    }

    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }
}

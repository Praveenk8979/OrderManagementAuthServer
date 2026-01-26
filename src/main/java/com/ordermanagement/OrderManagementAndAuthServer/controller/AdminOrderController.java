package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.ApiResponse;
import com.ordermanagement.OrderManagementAndAuthServer.model.Order;
import com.ordermanagement.OrderManagementAndAuthServer.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    @PutMapping("/{id}/paid")
    public ApiResponse<Order> markPaid(@PathVariable Long id) {

        Order order = service.markOrderPaid(id);

        return new ApiResponse<>(
                true,
                "Order marked as PAID. Payment email sent to user.",
                order
        );
    }
    @PreAuthorize("hasAuthority('ORDER_UPDATE')")
    @PutMapping("/{id}/delivered")
    public ApiResponse<Order> markDelivered(@PathVariable Long id) {

        Order order = service.markOrderDelivered(id);

        return new ApiResponse<>(
                true,
                "Order DELIVERED successfully. Delivery email sent to user.",
                order
        );
    }
}

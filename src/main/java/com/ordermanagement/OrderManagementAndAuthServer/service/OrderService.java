package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.model.Order;
import com.ordermanagement.OrderManagementAndAuthServer.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Long userId, List<OrderItem> items);
    List<Order> getUserOrders(Long userId);
    Order getOrderById(Long orderId);
    Order cancelOrder(Long orderId);

    Order markOrderPaid(Long orderId);
    Order markOrderDelivered(Long orderId);
}

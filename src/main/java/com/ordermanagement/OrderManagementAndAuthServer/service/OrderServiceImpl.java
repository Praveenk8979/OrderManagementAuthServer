package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.ExceptionHandler.ExceptionHandling;
import com.ordermanagement.OrderManagementAndAuthServer.dto.OrderStatus;
import com.ordermanagement.OrderManagementAndAuthServer.model.*;
import com.ordermanagement.OrderManagementAndAuthServer.repo.OrderRepo;
import com.ordermanagement.OrderManagementAndAuthServer.repo.OrderSummaryRepo;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo repo;
    @Autowired
    private EmaiService emaiService;

    @Autowired
    private OrderSummaryRepo orderSummaryRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InventoryService inventoryService;
    @Override
    public Order createOrder(Long userId, List<OrderItem> items) {
        double totalAmount=0;

        User user=userRepo.findById(userId)
                .orElseThrow(()-> new ExceptionHandling.UserNotFoundException("User ID: " + userId));

        //check stock and calculate total
        for(OrderItem item: items){
            Inventory product=inventoryService.getProductById(item.getProductId());
            if(product.getAvailableStock()<item.getQuantity()){
                throw  new ExceptionHandling.OutOfStockException(product.getProductName());
            }
            item.setPrice(product.getPrice());
            totalAmount +=item.getQuantity()*product.getPrice();

            inventoryService.updateStock(item.getProductId(),-item.getQuantity());
        }
        //minus stock
//        for(OrderItem item : items){
//            inventoryService.updateStock(item.getProductId(),-item.getQuantity());
//        }


        Order order=new Order();

        order.setUserId(user.getId());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        //set order items
        items.forEach(i -> i.setOrder(order));
        order.setItems(items);

        return repo.save(order);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return repo.findById(orderId).orElseThrow(()-> new ExceptionHandling.OrderNotFoundException(orderId));
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Order order=getOrderById(orderId);
        if(order.getStatus() != OrderStatus.CREATED){
            throw  new ExceptionHandling.InvalidOrderOperationException("Only pending orders can be cancelled!..");
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelAt(LocalDateTime.now());

        //Release Stock back
        for(OrderItem  item: order.getItems()){
            inventoryService.updateStock(item.getProductId(), item.getQuantity());
        }

        return repo.save(order);
    }

    @Override
    public Order markOrderPaid(Long orderId) {
        Order order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!."));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new ExceptionHandling.InvalidOrderOperationException("Only pending orders can be marked paid!..");
        }
        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        Order saveOrder = repo.save(order);

        //fetch email
        User user = userRepo.findById(order.getUserId())
                .orElseThrow(() -> new ExceptionHandling.UserNotFoundException("User not found!."));

        String paymentMailBody = "Hi " + user.getUserName() + ",\n\n" +
                "Your payment for Order ID: " + order.getId() + " has been received successfully.\n" +
                "Total Payment: Rs " + order.getTotalAmount() + "\n\n" +
                "Thank you for shopping with us!\n" +
                "Order Management Team";

        emaiService.sendEmail(user.getEmail(), "Payment Successful for Order " + order.getId(), paymentMailBody);

        return saveOrder;
    }

    @Override
    public Order markOrderDelivered(Long orderId) {
        Order order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Only paid orders can be delivered!");
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        Order savedOrder = repo.save(order);

        // Save order summary
        User user = userRepo.findById(order.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        int totalQuantity = order.getItems().stream().mapToInt(OrderItem::getQuantity).sum();

        OrderSummary summary = new OrderSummary();
        summary.setOrderId(order.getId());
        summary.setUserId(user.getId());
        summary.setUserName(user.getUserName());
        summary.setTotalQuantity(totalQuantity);
        summary.setTotalPayment(order.getTotalAmount());
        summary.setOrderStatus(OrderStatus.DELIVERED);
        summary.setDeliveredAt(LocalDateTime.now());

        orderSummaryRepo.save(summary);

        // Send email to user about delivery
        String deliveryMailBody = "Hi " + user.getUserName() + ",\n\n" +
                "Your Order ID: " + order.getId() + " has been successfully delivered!\n" +
                "Order Details:\n";

        for (OrderItem item : order.getItems()) {
            deliveryMailBody += "- " + item.getProductId() + " x " + item.getQuantity() + " (Rs " + item.getPrice() + " each)\n";
        }

        deliveryMailBody += "\nTotal Payment: Rs " + order.getTotalAmount() + "\n\n" +
                "Thank you for shopping with us!\n" +
                "Order Management Team";

        emaiService.sendEmail(user.getEmail(), "Order Delivered Successfully - Order " + order.getId(), deliveryMailBody);

        return savedOrder;
    }
}

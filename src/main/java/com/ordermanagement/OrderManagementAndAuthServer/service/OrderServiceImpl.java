package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.OrderStatus;
import com.ordermanagement.OrderManagementAndAuthServer.model.Inventory;
import com.ordermanagement.OrderManagementAndAuthServer.model.Order;
import com.ordermanagement.OrderManagementAndAuthServer.model.OrderItem;
import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.OrderRepo;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo repo;
    @Autowired
    private EmaiService emaiService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private InventoryService inventoryService;
    @Override
    public Order createOrder(Long userId, List<OrderItem> items) {
        double totalAmount=0;

        //check stock and calculate total
        for(OrderItem item: items){
            Inventory product=inventoryService.getProductById(item.getProductId());
            if(product.getAvailableStock()<item.getQuantity()){
                throw  new RuntimeException("Product "+product.getProductName() + " is out of Stock");
            }
            item.setPrice(product.getPrice());
            totalAmount +=item.getQuantity()*product.getPrice();
        }
        //reduce stock
        for(OrderItem item : items){
            inventoryService.updateStock(item.getProductId(),-item.getQuantity());
        }
        Order order=new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
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
        return repo.findById(orderId).orElseThrow(()-> new RuntimeException("Order not Found!.."));
    }

    @Override
    public Order cancelOrder(Long orderId) {
        Order order=getOrderById(orderId);
        if(order.getStatus() != OrderStatus.CREATED){
            throw  new RuntimeException("Only pending orders can be cancelled!..");
        }
        order.setStatus(OrderStatus.CANCELLED);

        //Release Stock back
        for(OrderItem  item: order.getItems()){
            inventoryService.updateStock(item.getProductId(), item.getQuantity());
        }

        return repo.save(order);
    }

    @Override
    public Order markOrderPaid(Long orderId) {
        Order order=getOrderById(orderId);

        if(order.getStatus() != OrderStatus.CREATED){
            throw  new RuntimeException("Order Cannot be marked as Paid!..");
        }
        order.setStatus(OrderStatus.PAID);
        Order saveOrder=repo.save(order);

        //fetch email
        User user=userRepo.findById(order.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found!.."));

        emaiService.sendEmail(
                user.getUserName(),
                "Payment Successfully",
                "Your payment for Order ID:"+order.getId()+" is successfully."
        );
        return saveOrder;
    }

    @Override
    public Order markOrderDelivered(Long orderId) {

        Order order=getOrderById(orderId);

        if(order.getStatus() !=OrderStatus.PAID){
            throw new RuntimeException("Only Paid orders can be delivered!.");
        }
        order.setStatus(OrderStatus.DELIVERED);
        Order saveOrder=repo.save(order);

        User user=userRepo.findById(order.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found!.."));

        emaiService.sendEmail(
                user.getUserName(),
                "Order Delivered",
                "Your order Id:"+order.getId()+" has been delivered successfully"
        );
        return saveOrder;
    }
}

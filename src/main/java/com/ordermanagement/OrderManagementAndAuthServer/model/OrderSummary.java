package com.ordermanagement.OrderManagementAndAuthServer.model;

import com.ordermanagement.OrderManagementAndAuthServer.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long userId;

    private String userName;

    private int totalQuantity;

    private double totalPayment;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime deliveredAt;
}

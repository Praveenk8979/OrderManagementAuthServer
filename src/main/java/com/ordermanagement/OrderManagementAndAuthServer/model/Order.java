package com.ordermanagement.OrderManagementAndAuthServer.model;

import com.ordermanagement.OrderManagementAndAuthServer.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , length = 30)
    private OrderStatus status;

    private Double totalAmount;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private LocalDateTime createdAt=LocalDateTime.now();

}

package com.ordermanagement.OrderManagementAndAuthServer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double percentage;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private  boolean active;
}

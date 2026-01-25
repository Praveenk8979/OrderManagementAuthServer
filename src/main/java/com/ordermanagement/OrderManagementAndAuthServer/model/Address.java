package com.ordermanagement.OrderManagementAndAuthServer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String name;
    private String mobile;

    private String street;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private boolean defaultAddress;



}

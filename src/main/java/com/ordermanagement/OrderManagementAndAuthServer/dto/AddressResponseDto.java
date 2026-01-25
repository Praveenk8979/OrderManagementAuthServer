package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class AddressResponseDto {


    private Long id;
    private Long userId;
    private String name;
    private String mobile;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private boolean defaultAddress;
}

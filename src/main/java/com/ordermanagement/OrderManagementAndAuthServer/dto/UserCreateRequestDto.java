package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class UserCreateRequestDto {

    private String userName;
    private String password;
    private String role;
    private String status;

}

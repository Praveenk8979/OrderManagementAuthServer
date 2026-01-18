package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class UserDto {

    private long id;
    private String userName;
    private String password;
    private String role;
    private String status;
}

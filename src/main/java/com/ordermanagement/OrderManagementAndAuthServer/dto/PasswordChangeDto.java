package com.ordermanagement.OrderManagementAndAuthServer.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}

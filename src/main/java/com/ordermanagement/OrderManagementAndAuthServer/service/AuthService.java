package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginResponceDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.UserCreateRequestDto;

public interface AuthService {

    public LoginResponceDto login(LoginRequestDto dto);
    public String registerUser(UserCreateRequestDto dto);
}

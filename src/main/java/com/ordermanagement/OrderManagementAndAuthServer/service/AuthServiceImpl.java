package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.dto.*;
import com.ordermanagement.OrderManagementAndAuthServer.model.User;
import com.ordermanagement.OrderManagementAndAuthServer.repo.UserRepo;
import com.ordermanagement.OrderManagementAndAuthServer.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements  AuthService{

    @Autowired
    private UserRepo repo;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public LoginResponceDto login(LoginRequestDto dto){
        //authencation user
        Authentication authentication=manager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUserName(),dto.getPassword())
        );

        //JWT tokens
        String accessToken = jwtUtil.generateAccessToken(dto.getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(dto.getUserName());

        return new LoginResponceDto(accessToken, refreshToken);
    }
    @Override
    public String registerUser(UserCreateRequestDto dto) {

        // check duplicate
        if (repo.findByUserName(dto.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(encoder.encode(dto.getPassword()));

        // Set roles from enum
        if (dto.getRole() != null) {
            user.setRoles(Set.of(UserRole.valueOf(dto.getRole().toUpperCase())));
        } else {
            user.setRoles(Set.of(UserRole.USER)); // default safe
        }

        // Set status
        user.setStatus(UserStatus.ACTIVE);

        repo.save(user);

        return "User registered successfully";
    }
    }


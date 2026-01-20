package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginResponceDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.UserCreateRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.security.JwtUtil;
import com.ordermanagement.OrderManagementAndAuthServer.service.AuthService;
import com.ordermanagement.OrderManagementAndAuthServer.service.LoginEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService service;

    @Autowired
    private LoginEventProducer producer;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateRequestDto req){
        String res=service.registerUser(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto req) {
        // Authenticate user
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUserName(),
                        req.getPassword()
                )
        );

        // Generate access + refresh token
        String accessToken = jwtUtil.generateAccessToken(req.getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(req.getUserName());

        // Return tokens in response
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);
    }
}

package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginResponceDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.UserCreateRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.security.JwtUtil;
import com.ordermanagement.OrderManagementAndAuthServer.service.AuthService;
import com.ordermanagement.OrderManagementAndAuthServer.service.LoginEventProducer;
import com.ordermanagement.OrderManagementAndAuthServer.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil util;

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
    public ResponseEntity<LoginResponceDto> login(@RequestBody LoginRequestDto req){
        Authentication auth= manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUserName(),req.getPassword())
        );
        UserDetailsService detailsService=(UserDetailsService) auth.getPrincipal();

        return ResponseEntity.ok(
                new LoginResponceDto(
                        util.generateAccessToken(detailsService.toString()),
                        util.generateRefreshToken(detailsService.toString())
                )
        );
    }
}

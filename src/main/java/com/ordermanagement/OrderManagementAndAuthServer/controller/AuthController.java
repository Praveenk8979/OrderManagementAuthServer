package com.ordermanagement.OrderManagementAndAuthServer.controller;

import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginRequestDto;
import com.ordermanagement.OrderManagementAndAuthServer.dto.LoginResponceDto;
import com.ordermanagement.OrderManagementAndAuthServer.security.JwtUtil;
import com.ordermanagement.OrderManagementAndAuthServer.service.LoginEventProducer;
import com.ordermanagement.OrderManagementAndAuthServer.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private LoginEventProducer producer;

    public ResponseEntity<LoginResponceDto> login(@RequestBody LoginRequestDto req){
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUserName(),
                        req.getPassword()));
        producer.publish(req.getUserName());

        return ResponseEntity.ok(
                new LoginResponceDto(
                        util.generateAccessToken(req.getUserName()),
                        util.generateRefreshToken(req.getUserName())
                )
        );
    }
}

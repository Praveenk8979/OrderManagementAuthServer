package com.ordermanagement.OrderManagementAndAuthServer.service;

import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements  EmaiService{
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("Email Send...");
        System.out.println("To:"+to);
        System.out.println("Subject:"+subject);
        System.out.println("Body:"+body);
    }
}

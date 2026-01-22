package com.ordermanagement.OrderManagementAndAuthServer.service;

public interface EmaiService {
    void sendEmail(String to, String object, String body);
}

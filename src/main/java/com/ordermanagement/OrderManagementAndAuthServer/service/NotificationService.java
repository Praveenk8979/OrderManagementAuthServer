package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.model.Notifications;

import java.util.List;

public interface NotificationService {

    Notifications sendNotification(Notifications notification);
    List<Notifications> getUserNotifications(Long userId);
}

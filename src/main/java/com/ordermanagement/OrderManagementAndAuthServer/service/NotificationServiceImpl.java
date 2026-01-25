package com.ordermanagement.OrderManagementAndAuthServer.service;

import com.ordermanagement.OrderManagementAndAuthServer.model.Notifications;
import com.ordermanagement.OrderManagementAndAuthServer.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationServiceImpl implements  NotificationService{

    @Autowired
    private NotificationRepo notificationRepo;
    @Override
    public Notifications sendNotification(Notifications notification) {
        return notificationRepo.save(notification);
    }

    @Override
    public List<Notifications> getUserNotifications(Long userId) {
        return notificationRepo.findByUserId(userId);
    }
}

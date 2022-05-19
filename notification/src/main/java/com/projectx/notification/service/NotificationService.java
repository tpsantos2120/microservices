package com.projectx.notification.service;

import com.projectx.clients.notification.NotificationRequest;

public interface NotificationService {

    void sendNotification(NotificationRequest notificationRequest);
}

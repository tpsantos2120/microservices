package com.projectx.notification.controller;

import com.projectx.clients.notification.NotificationRequest;
import com.projectx.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("api/v1/notification")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("New notification... {}", notificationRequest);
        notificationService.sendNotification(notificationRequest);
    }
}

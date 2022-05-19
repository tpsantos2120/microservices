package com.projectx.notification.service;

import com.projectx.clients.notification.NotificationRequest;
import com.projectx.notification.model.Notification;
import com.projectx.notification.repository.NotificationRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .phoneNumber(notificationRequest.phoneNumber())
                        .firstName(notificationRequest.firstName())
                        .lastName(notificationRequest.lastName())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
        try {
            Message.creator(
                    new PhoneNumber(notificationRequest.phoneNumber()),
                    new PhoneNumber("+17197454481"),
                    "Hello, " + notificationRequest.firstName() + "welcome to our nutrition program.").create();
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}

package com.projectx.notification.repository;

import com.projectx.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository  extends JpaRepository<Notification, Integer> {
}

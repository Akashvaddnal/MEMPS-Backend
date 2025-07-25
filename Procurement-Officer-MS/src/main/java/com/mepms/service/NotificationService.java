package com.mepms.service;

import java.util.List;
import java.util.Optional;

import com.mepms.entity.Notification;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Optional<Notification> getNotificationById(String id);
    List<Notification> getNotificationsByRecipientId(String recipientId);
    List<Notification> getUnreadNotificationsByRecipientId(String recipientId);
    Notification createNotification(Notification notification);
    Notification updateNotification(String id, Notification notification);
    void deleteNotification(String id);

    // Mark notification as read
    Notification markAsRead(String id);
}

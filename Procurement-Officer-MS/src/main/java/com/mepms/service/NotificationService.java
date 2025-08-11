package com.mepms.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import com.mepms.entity.Notification;
//
//public interface NotificationService {
//    List<Notification> getAllNotifications();
//    Optional<Notification> getNotificationById(String id);
//    List<Notification> getNotificationsByRecipientId(String recipientId);
//    List<Notification> getUnreadNotificationsByRecipientId(String recipientId);
//    Notification createNotification(Notification notification);
//    Notification updateNotification(String id, Notification notification);
//    void deleteNotification(String id);
//
//    // Mark notification as read
//    Notification markAsRead(String id);
//}





import java.util.List;

import com.mepms.entity.NotificationRecord;

public interface NotificationService {
    NotificationRecord create(NotificationRecord notification);
    List<NotificationRecord> getAll();
    NotificationRecord getById(String id);
    List<NotificationRecord> getByUserId(String userId);
    List<NotificationRecord> getUnreadByUserId(String userId);
    void markAsRead(String id);
    NotificationRecord addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc);
}

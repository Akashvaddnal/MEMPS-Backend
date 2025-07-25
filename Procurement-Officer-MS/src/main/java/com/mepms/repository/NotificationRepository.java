package com.mepms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientId(String recipientId);
    List<Notification> findByIsReadFalseAndRecipientId(String recipientId);
}

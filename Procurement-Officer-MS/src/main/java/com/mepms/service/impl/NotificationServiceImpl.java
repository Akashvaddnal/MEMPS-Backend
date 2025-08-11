//package com.mepms.service.impl;
//
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.mepms.entity.NotificationRecord;
//import com.mepms.repository.NotificationRecordRepository;
//import com.mepms.service.NotificationService;
//import com.mepms.rabbitmq.config.NotificationMessagePublisher;
//
//@Service
//public class NotificationServiceImpl implements NotificationService {
//
//    @Autowired
//    private NotificationRecordRepository repository;
//
//    @Autowired
//    private NotificationMessagePublisher notificationPublisher;
//    
//    @Override
//    public List<NotificationRecord> getAll() {
//    	return repository.findAll();
//    }
//
//    @Override
//    public NotificationRecord create(NotificationRecord notification) {
//        notification.setRead(false);
//        notification.setCreatedAt(java.time.LocalDateTime.now());
//        NotificationRecord saved = repository.save(notification);
//        notificationPublisher.sendNotification(saved); // Publish for async + websocket
//        return saved;
//    }
//    @Override
//    public NotificationRecord getById(String id) {
//        return repository.findById(id).orElse(null);
//    }
//    @Override
//    public List<NotificationRecord> getByUserId(String userId) {
//        return repository.findByUserId(userId);
//    }
//    @Override
//    public List<NotificationRecord> getUnreadByUserId(String userId) {
//        return repository.findByUserIdAndRead(userId, false);
//    }
//    @Override
//    public void markAsRead(String id) {
//        Optional<NotificationRecord> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            NotificationRecord record = optional.get();
//            record.setRead(true);
//            repository.save(record);
//        }
//    }
//    // -- NEW: add document/file to notification's document array
//    @Override
//    public NotificationRecord addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc) {
//        Optional<NotificationRecord> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            NotificationRecord notif = optional.get();
//            if (notif.getDocuments() == null) notif.setDocuments(new java.util.ArrayList<>());
//            notif.getDocuments().add(doc);
//            return repository.save(notif);
//        }
//        return null;
//    }
//}


package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mepms.entity.NotificationRecord;
import com.mepms.repository.NotificationRecordRepository;
import com.mepms.service.NotificationService;
import com.mepms.rabbitmq.config.NotificationMessagePublisher;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRecordRepository repository;

    @Autowired
    private NotificationMessagePublisher notificationPublisher;

    @Autowired(required = false)
    private SimpMessagingTemplate wsTemplate; // WebSocket pushing, can be null if WS not enabled

    // Get all notifications
    @Override
    public List<NotificationRecord> getAll() {
        return repository.findAll();
    }

    // Create notification: save + publish via RabbitMQ + push via WebSocket if available
    @Override
    public NotificationRecord create(NotificationRecord notification) {
        notification.setRead(false);
        notification.setCreatedAt(java.time.LocalDateTime.now());
        NotificationRecord saved = repository.save(notification);
        // Publish via RabbitMQ
        notificationPublisher.sendNotification(saved);
        // Push real-time notification via WebSocket
        if (wsTemplate != null) {
            wsTemplate.convertAndSend("/topic/notifications/" + saved.getUserId(), saved);
        }
        return saved;
    }

    @Override
    public NotificationRecord getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<NotificationRecord> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<NotificationRecord> getUnreadByUserId(String userId) {
        return repository.findByUserIdAndRead(userId, false);
    }

    @Override
    public void markAsRead(String id) {
        Optional<NotificationRecord> optional = repository.findById(id);
        if (optional.isPresent()) {
            NotificationRecord record = optional.get();
            record.setRead(true);
            repository.save(record);
        }
    }

    @Override
    public NotificationRecord addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc) {
        Optional<NotificationRecord> optional = repository.findById(id);
        if (optional.isPresent()) {
            NotificationRecord notif = optional.get();
            if (notif.getDocuments() == null) {
                notif.setDocuments(new java.util.ArrayList<>());
            }
            notif.getDocuments().add(doc);
            NotificationRecord updated = repository.save(notif);
            // Optionally push update to WebSocket clients if needed
            if (wsTemplate != null) {
                wsTemplate.convertAndSend("/topic/notifications/" + updated.getUserId(), updated);
            }
            return updated;
        }
        return null;
    }
}

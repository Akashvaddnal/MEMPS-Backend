//package com.mepms.service;
//
//import java.util.List;
//
//import com.mepms.entity.NotificationRecord;
//
//public interface NotificationService {
//    NotificationRecord save(NotificationRecord record);
//    List<NotificationRecord> findAll();
//    List<NotificationRecord> findByUserId(String userId);
//    List<NotificationRecord> filterByTypeAndRead(String type, boolean read);
//    void markAsRead(String notificationId);
//    void addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc);
//    void deleteById(String id);
//    // ...other filter methods as needed
//}


//package com.mepms.service;
//
//import java.util.List;
//
//import com.mepms.entity.NotificationRecord;
//
//public interface NotificationService {
//
//    NotificationRecord create(NotificationRecord notification);
//
//    NotificationRecord update(NotificationRecord notification);
//
//    void deleteById(String id);
//
//    NotificationRecord getById(String id);
//
//    List<NotificationRecord> getAll();
//
//    List<NotificationRecord> getByUserId(String userId);
//
//    List<NotificationRecord> getByType(String type);
//
//    List<NotificationRecord> getUnreadByUserId(String userId);
//
//    void markAsRead(String id);
//
//    void addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc);
//}



package com.mepms.service;

import com.mepms.entity.NotificationRecord;
import java.util.List;

public interface NotificationService {
    NotificationRecord create(NotificationRecord notification);
    NotificationRecord update(NotificationRecord notification);
    void deleteById(String id);
    NotificationRecord getById(String id);
    List<NotificationRecord> getAll();
    List<NotificationRecord> getByUserId(String userId);
    List<NotificationRecord> getByType(String type);
    List<NotificationRecord> getUnreadByUserId(String userId);
    void markAsRead(String id);
    // NEW
    NotificationRecord addDocumentToNotification(String id, NotificationRecord.DocumentAttachment doc);
}

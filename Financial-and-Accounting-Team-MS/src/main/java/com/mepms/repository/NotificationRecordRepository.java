package com.mepms.repository;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.NotificationRecord;

@Repository
public interface NotificationRecordRepository extends MongoRepository<NotificationRecord, String> {
    List<NotificationRecord> findByUserId(String userId);
    List<NotificationRecord> findByType(String type);
    List<NotificationRecord> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<NotificationRecord> findByUserIdAndRead(String userId, boolean read);
}

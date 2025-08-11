package com.mepms.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.NotificationRecord;

@Repository
public interface NotificationRecordRepository extends MongoRepository<NotificationRecord, String> {
    List<NotificationRecord> findByUserId(String userId);
    List<NotificationRecord> findByUserIdAndRead(String userId, boolean read);
}

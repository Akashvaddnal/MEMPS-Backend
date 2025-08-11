package com.mepms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.EmailRecord;

@Repository
public interface EmailRecordRepository extends MongoRepository<EmailRecord, String> {
    List<EmailRecord> findByRecipientsContaining(String email);
    List<EmailRecord> findBySentAtBetween(LocalDateTime from, LocalDateTime to);
    List<EmailRecord> findBySuccess(boolean success);
}

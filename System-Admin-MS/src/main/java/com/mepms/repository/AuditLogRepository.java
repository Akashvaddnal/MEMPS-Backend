package com.mepms.repository;

import com.mepms.entity.AuditLogEO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogEO, String> {
    List<AuditLogEO> findByUserId(String userId);
    List<AuditLogEO> findByAction(String action);
}

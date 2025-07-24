package com.mepms.service;

import com.mepms.entity.AuditLogEO;

import java.util.List;
import java.util.Optional;

public interface AuditLogService {
    AuditLogEO saveAuditLog(AuditLogEO log);
    List<AuditLogEO> getAllLogs();
    List<AuditLogEO> getLogsByUserId(String userId);
    List<AuditLogEO> getLogsByAction(String action);
    Optional<AuditLogEO> getLogById(String id);
    void deleteLog(String id);
}

package com.mepms.service.impl;

import com.mepms.entity.AuditLogEO;
import com.mepms.repository.AuditLogRepository;
import com.mepms.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLogEO saveAuditLog(AuditLogEO log) {
        return auditLogRepository.save(log);
    }

    @Override
    public List<AuditLogEO> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public List<AuditLogEO> getLogsByUserId(String userId) {
        return auditLogRepository.findByUserId(userId);
    }

    @Override
    public List<AuditLogEO> getLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    @Override
    public Optional<AuditLogEO> getLogById(String id) {
        return auditLogRepository.findById(id);
    }

    @Override
    public void deleteLog(String id) {
        auditLogRepository.deleteById(id);
    }
}

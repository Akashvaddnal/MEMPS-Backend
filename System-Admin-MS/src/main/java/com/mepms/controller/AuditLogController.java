package com.mepms.controller;

import com.mepms.entity.AuditLogEO;
import com.mepms.service.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "*")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Autowired
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    // Create Log
    @PostMapping
    public ResponseEntity<AuditLogEO> createLog(@Valid @RequestBody AuditLogEO log) {
        return ResponseEntity.ok(auditLogService.saveAuditLog(log));
    }

    // Get All Logs
    @GetMapping
    public ResponseEntity<List<AuditLogEO>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    // Get Log by ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogEO> getLogById(@PathVariable String id) {
        return auditLogService.getLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get Logs by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogEO>> getLogsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(auditLogService.getLogsByUserId(userId));
    }

    // Get Logs by Action
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogEO>> getLogsByAction(@PathVariable String action) {
        return ResponseEntity.ok(auditLogService.getLogsByAction(action));
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        auditLogService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}

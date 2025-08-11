package com.mepms.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mepms.entity.NotificationRecord;
import com.mepms.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationRecordController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public NotificationRecord create(@RequestBody NotificationRecord notificationRecord) {
        notificationRecord.setCreatedAt(LocalDateTime.now());
        NotificationRecord created = notificationService.create(notificationRecord);
        // Optionally send notification or message here
        return created;
    }

    @GetMapping
    public List<NotificationRecord> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public NotificationRecord getById(@PathVariable String id) {
        return notificationService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationRecord> getByUserId(@PathVariable String userId) {
        return notificationService.getByUserId(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationRecord> getUnreadByUserId(@PathVariable String userId) {
        return notificationService.getUnreadByUserId(userId);
    }

    @PatchMapping("/{id}/read")
    public void markAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
    }

    @PostMapping("/{id}/documents")
    public void addDocument(@PathVariable String id, @RequestBody NotificationRecord.DocumentAttachment doc) {
        notificationService.addDocumentToNotification(id, doc);
    }

    @PutMapping("/{id}")
    public NotificationRecord update(@PathVariable String id, @RequestBody NotificationRecord notificationRecord) {
        notificationRecord.setId(id);
        return notificationService.update(notificationRecord);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        notificationService.deleteById(id);
    }
}

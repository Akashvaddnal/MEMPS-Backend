
package com.mepms.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mepms.entity.NotificationRecord;
import com.mepms.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping
    public List<NotificationRecord> getAll(){
    	return notificationService.getAll();
    }

    @PostMapping
    public NotificationRecord createNotification(@RequestBody NotificationRecord notification) {
        return notificationService.create(notification);
    }
    @GetMapping("/{id}")
    public NotificationRecord getNotificationById(@PathVariable String id) {
        return notificationService.getById(id);
    }
    @GetMapping("/user/{userId}")
    public List<NotificationRecord> getNotificationsByUserId(@PathVariable String userId) {
        return notificationService.getByUserId(userId);
    }
    @GetMapping("/user/{userId}/unread")
    public List<NotificationRecord> getUnreadNotificationsByUserId(@PathVariable String userId) {
        return notificationService.getUnreadByUserId(userId);
    }
    @PatchMapping("/{id}/read")
    public void markNotificationAsRead(@PathVariable String id) {
        notificationService.markAsRead(id);
    }
    @PostMapping("/{id}/documents")
    public NotificationRecord addDocumentToNotification(
            @PathVariable String id,
            @RequestBody NotificationRecord.DocumentAttachment doc) {
        return notificationService.addDocumentToNotification(id, doc);
    }
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id) {
        // Optional: implement as needed
    }
}

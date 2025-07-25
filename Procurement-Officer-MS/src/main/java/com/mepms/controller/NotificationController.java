package com.mepms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.Notification;
import com.mepms.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Optional<Notification> getById(@PathVariable String id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/recipient/{recipientId}")
    public List<Notification> getByRecipient(@PathVariable String recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId);
    }

    @GetMapping("/recipient/{recipientId}/unread")
    public List<Notification> getUnreadByRecipient(@PathVariable String recipientId) {
        return notificationService.getUnreadNotificationsByRecipientId(recipientId);
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/{id}")
    public Notification update(@PathVariable String id, @RequestBody Notification notification) {
        return notificationService.updateNotification(id, notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        notificationService.deleteNotification(id);
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(@PathVariable String id) {
        return notificationService.markAsRead(id);
    }
}

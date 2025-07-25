package com.mepms.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String notificationType;
    private String recipientId;
    private String message;
    private Instant createdAt;
    private boolean isRead;
}

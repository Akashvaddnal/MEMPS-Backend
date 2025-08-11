package com.mepms.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="notification_records")
@Data
@NoArgsConstructor @AllArgsConstructor
public class NotificationRecord {
    @Id
    private String id;
    private String userId;
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
    private String type; // SYSTEM, USER, ALERT, etc.
    private List<DocumentAttachment> documents;

    @Data public static class DocumentAttachment {
        private String name;
        private String type;
        private long size;
        private byte[] data;
    }
}

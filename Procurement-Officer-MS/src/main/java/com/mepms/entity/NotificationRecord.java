
package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notification_records")
@Data
public class NotificationRecord {
    @Id
    private String id;
    private String userId;
    private String title;
    private String message;
    private boolean read;
    private String type;
    private LocalDateTime createdAt;
    private List<DocumentAttachment> documents;

    @Data
    public static class DocumentAttachment {
        private String name;
        private String type;
        private long size;
        private byte[] data;
        // Standard getters/setters via Lombok
    }
}

package com.mepms.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="email_records")
@Data
@NoArgsConstructor @AllArgsConstructor
public class EmailRecord {
    @Id
    private String id;
    private List<String> recipients;
    private String subject;
    private String content;
    private LocalDateTime sentAt;
    private boolean success;
    private String errorMessage;
    private List<DocumentAttachment> documents;

    @Data public static class DocumentAttachment {
        private String name;
        private String type;
        private long size;
        private byte[] data;
    }
}

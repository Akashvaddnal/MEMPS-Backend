package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "audit_logs")
public class AuditLogEO {

    @Id
    private String id;

    @NotBlank(message = "User ID is required")
    @Field("user_id")
    private String userId;

    @NotBlank(message = "Action is required")
    private String action;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @NotBlank(message = "Details are required")
    private String details;
}

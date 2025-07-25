package com.mepms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory_audit")
public class InventoryAudit {
    @Id
    private String id;
    private String performedBy;
    private String performedAt;
    private String findings;
}

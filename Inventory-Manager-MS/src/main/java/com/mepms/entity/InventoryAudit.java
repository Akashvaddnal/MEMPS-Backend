//package com.mepms.entity;
//
//import lombok.Data;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Data
//@Document(collection = "inventory_audit")
//public class InventoryAudit {
//    @Id
//    private String id;
//    private String performedBy;
//    private String performedAt;
//    private String findings;
//}


package com.mepms.entity;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "inventory_audits")
public class InventoryAudit {
    @Id
    private String id;
    private String auditType;      // "Full", "Partial", "Spot"
    private Instant datePerformed;
    private String performedBy;    // User ID or name
    private int itemsChecked;
    private int discrepancies;
    private String status;         // "Completed", "Failed", "Partial"
    private String notes;
    private Instant createdAt;
    private Instant updatedAt;
}

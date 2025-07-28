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

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "inventory_audits")
public class InventoryAudit {
    @Id
    private String id;
    private String auditType;      // "Full", "Partial", "Spot"
    private Date datePerformed;
    private String performedBy;    // User ID or name
    private int itemsChecked;
    private int discrepancies;
    private String status;         // "Completed", "Failed", "Partial"
    private String notes;
    private Date createdAt;
    private Date updatedAt;
}

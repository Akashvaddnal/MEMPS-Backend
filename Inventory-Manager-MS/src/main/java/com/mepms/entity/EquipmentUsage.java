package com.mepms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "equipment_usage")
public class EquipmentUsage {
    @Id
    private String id;
    private String equipmentId;
    private String usedBy;
    private String reservedBy;
    private String usageStart;
    private String usageEnd;
    private String purpose;
    private String status;
}

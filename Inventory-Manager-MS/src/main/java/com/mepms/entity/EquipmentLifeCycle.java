package com.mepms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "equipment_lifecycle")
public class EquipmentLifeCycle {
    @Id
    private String id;
    private String equipmentId;
    private String acquisitionDate;
    private String expectedEndOfLife;
    private int maintenanceCount;
    private double totalMaintenanceCost;
}

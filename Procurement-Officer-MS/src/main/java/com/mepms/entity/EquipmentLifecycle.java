package com.mepms.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "equipment_lifecycle")
public class EquipmentLifecycle {
    @Id
    private String id;

    private String equipmentId;
    private Date acquisitionDate;
    private Date expectedEndOfLife;
    private Integer maintenanceCount;
    private Double totalMaintenanceCost;
}

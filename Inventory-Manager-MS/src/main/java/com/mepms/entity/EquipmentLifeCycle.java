package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "equipment_lifecycle")
public class EquipmentLifeCycle {
    @Id
    private String id;
    @Field("equipment_id")
    private String equipmentId;
    @Field("acquisition_date")
    private java.util.Date acquisitionDate;
    @Field("expected_end_of_life")
    private java.util.Date expectedEndOfLife;
    @Field("maintenance_count")
    private int maintenanceCount;
    @Field("total_maintenance_cost")
    private double totalMaintenanceCost;
    @Field("warranty_expiry")
    private java.util.Date warrantyExpirationDate;
    
    @Field("last_maintenance_date")
    private java.util.Date lastMaintenanceDate;
    
    private java.util.Date nextMaintenanceDate;
}

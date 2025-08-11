package com.mepms.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Document(collection = "maintenance_requests")
@Data
public class MaintenanceRequest {
    @Id
    private String id;
    private String equipmentId;
    
    private String unitId;
    
    private String reportedBy;
    private String department;
    private String technicianId;
    private String issueDescription;
    private String status; // Pending, In Progress, Resolved, etc.
    private Instant reportedAt;
    private Instant resolvedAt;
    private String maintenanceType; // Preventive, Corrective
    private String maintenanceNotes;
    @Field("accepted")
    private Boolean accepted; // Indicates if the request has been accepted by a technician

    // Getters and setters
}

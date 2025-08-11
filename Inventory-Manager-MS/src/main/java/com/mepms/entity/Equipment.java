package com.mepms.entity;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "equipment")
public class Equipment {
    @Id
    private String id;
    private String name;
    private String model;
    private String serialNumber;
    private String category;
    private Instant purchaseDate;
//    private String purchaseDate;
    private Instant warrantyEndDate;
    private String status;
    private int quantity;
    private String location;
    private String vendorId;
    private int expectedLife;
    private Instant createdAt;
    private Instant updatedAt;
    private String image;
    private int currentStock; 
}

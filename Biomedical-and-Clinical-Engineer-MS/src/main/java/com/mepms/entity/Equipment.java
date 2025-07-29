package com.mepms.entity;

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
    private LocalDateTime purchaseDate;
//    private String purchaseDate;
    private LocalDateTime warrantyEndDate;
    private String status;
    private int quantity;
    private String location;
    private String vendorId;
    private int expectedLife;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String image;
    private int currentStock; 
}

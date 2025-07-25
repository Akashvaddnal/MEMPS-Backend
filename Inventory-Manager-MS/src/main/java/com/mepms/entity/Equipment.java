package com.mepms.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "equipment")
public class Equipment {
    @Id
    private String id;
    private String name;
    private String model;
    private String serialNumber;
    private String category;
    private String purchaseDate;
    private String warrantyEndDate;
    private String status;
    private String location;
    private String vendorId;
    private int expectedLife;
    private String createdAt;
    private String updatedAt;
}

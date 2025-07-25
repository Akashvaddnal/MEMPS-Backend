package com.mepms.entity;

import java.util.Date;

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
    private Date purchaseDate;
    private Date warrantyEndDate;
    private String status;
    private String location;
    private String vendorId;
    private Integer expectedLife;
    private Date createdAt;
    private Date updatedAt;
}

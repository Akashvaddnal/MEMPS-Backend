package com.mepms.entity;


import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "departments")
@Data
public class Department {
    @Id
    private String id;

    private String name;
    private String location;
    private String contactPerson;
    private String phone;
    private String email;

    // Map of Equipment ID to count
    private Map<String, Integer> equipmentInventory;

    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
    
    // constructors, equals, hashcode, toString
}

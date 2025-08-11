
package com.mepms.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "equipment")
public class Equipment {
    @Id
    private String _id;
    
    @Field("vendorId")
    private String vendorId;
    private String name;
    private String model;
    
    @Field("serialNumber")
    private String serialNumber;
    private String category;
    private String status;
    private String location;
    
    @Field("createdAt")
    private String createdAt;
    
    @Field("updatedAt")
    private String updatedAt;
    
}
package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "vendors")
public class Vendor {
    @Id
    private String id; // "_id" field in Mongo

    private String name;
    @Field("contact_person")
    private String contactPerson;
    @Indexed(unique = true)
    private String email;
    private String phone;
    private String address;
}

package com.mepms.entity;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "budget")
@Data
public class Budget {
    @Id
    private String id;
    private double amount;
    private double remaining;
    private double spent;
    private String currency;
    private int year;
    private LocalDateTime lastUpdated;
    private String notes;

    
}

package com.mepms.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "stock_levels")
public class StockLevel {
    @Id
    private String id;

    private String equipmentId;
    @NotNull(message = "CurrentQuantity value is required")
    private Integer currentQuantity;
    @NotNull(message = "minimun  quantity value is required")
    private Integer minRequired;
    private Date lastChecked;
}

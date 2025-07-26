package com.mepms.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "stock_levels")
public class StockLevel {
    @Id
    private String id;
    
    @Field("equipment_id")
    private String equipmentId;
    @NotNull(message = "CurrentQuantity value is required")
    @Field("current_quantity")
    private Integer currentQuantity;
    @NotNull(message = "minimun  quantity value is required")
    @Field("min_required")
    private Integer minRequired;
    @Field("last_checked")
    private Date lastChecked;
}

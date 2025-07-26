package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "purchase_order_items")
public class PurchaseOrderItem {
    @Id
    private String id;
    
    @Field("po_id")
    @Indexed
    private String poId;
    
    @Field("equipment_id")
    private String equipmentId;
    
    private Integer quantity;
    @Field("unit_price")
    private Double unitPrice;
    private Double total;
}

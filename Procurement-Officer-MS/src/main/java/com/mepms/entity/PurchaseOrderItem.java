package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "purchase_order_items")
public class PurchaseOrderItem {
    @Id
    private String id;

    private String poId;
    private String equipmentId;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}

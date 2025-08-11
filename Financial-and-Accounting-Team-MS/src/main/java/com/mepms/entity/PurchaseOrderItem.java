package com.mepms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="purchase_order_items")
@Data
@NoArgsConstructor @AllArgsConstructor
public class PurchaseOrderItem {
    @Id
    private String id;
    private String poId;
    private String equipmentId;
    private int quantity;
    private double unitPrice;
    private double total;
}

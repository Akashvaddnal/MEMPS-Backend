package com.mepms.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="purchase_orders")
@Data
@NoArgsConstructor @AllArgsConstructor
public class PurchaseOrder {
    @Id
    private String id;
    
    @Field("po_number")
    @Indexed(unique = true)
    private String poNumber;
    
    @Field("date_issued")
    private LocalDateTime dateIssued;
    private String status; // Pending, Approved, Rejected, etc.
    
    @Field("vendor_id")
    private String vendorId;
    
    @Field("requested_by")
    private String requestedBy;
    @Field("total_amount")
    private double totalAmount;
    private List<POItem> items;
    @Field("delivery_date")
    private LocalDateTime deliveryDate;
    
    @Field("payment_status")
    private String paymentStatus;
    private String priority;
    private String notes;

    @Data public static class POItem {
    	@Field("equipment_id")
        private String equipmentId;
    	
        private int quantity;
        @Field("unit_price")
        private double unitPrice;
        private double total;
    }
}

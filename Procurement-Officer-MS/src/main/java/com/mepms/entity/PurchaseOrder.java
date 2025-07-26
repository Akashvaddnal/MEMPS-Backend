package com.mepms.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "purchase_orders")
public class PurchaseOrder {
    @Id
    private String id; // "_id" in MongoDB
    
    @Field("po_number")
    @Indexed(unique = true)
    private String poNumber;
    
    @Field("date_issued")
    private Date dateIssued;
    
    private String status; // e.g. Delivered, Pending
    @Field("vendor_id")
    private String vendorId;
    @Field("requested_by")
    private String requestedBy;
    @Field("approved_by")
    private String approvedBy;
    @Field("total_amount")
    private Double totalAmount;
    
    private List<PurchaseOrderItem> items;
    @Field("delivery_date")
    private Date deliveryDate;
    @Field("payment_status")
    private String paymentStatus; // e.g. Paid, Due
    
    private String priority; // e.g. High, Medium, Low
    private String notes; // Additional notes or comments
}

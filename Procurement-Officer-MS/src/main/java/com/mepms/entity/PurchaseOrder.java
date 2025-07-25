package com.mepms.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "purchase_orders")
public class PurchaseOrder {
    @Id
    private String id; // "_id" in MongoDB

    private String poNumber;
    private Date dateIssued;
    private String status; // e.g. Delivered, Pending
    private String vendorId;
    private String requestedBy;
    private String approvedBy;
    private Double totalAmount;
    private Date deliveryDate;
    private String paymentStatus; // e.g. Paid, Due
}

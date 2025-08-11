package com.mepms.service;


import java.time.LocalDateTime;
import java.util.List;

import com.mepms.entity.PurchaseOrder;


public interface PurchaseOrderService {

    PurchaseOrder create(PurchaseOrder purchaseOrder);

    PurchaseOrder update(PurchaseOrder purchaseOrder);

    void deleteById(String id);

    PurchaseOrder getById(String id);

    PurchaseOrder getByPoNumber(String poNumber);

    List<PurchaseOrder> getAll();

    List<PurchaseOrder> getByStatus(String status);

    List<PurchaseOrder> getByVendorId(String vendorId);

    List<PurchaseOrder> getByDateIssuedBetween(LocalDateTime start, LocalDateTime end);

    List<PurchaseOrder> getByPriority(String priority);

    List<PurchaseOrder> getByRequestedBy(String requester);

    // Additional: add/remove/update PO items embedded in purchase order
    PurchaseOrder addItemToPurchaseOrder(String purchaseOrderId, PurchaseOrder.POItem newItem);

    PurchaseOrder updateItemInPurchaseOrder(String purchaseOrderId, String itemId, PurchaseOrder.POItem updatedItem);

    PurchaseOrder removeItemFromPurchaseOrder(String purchaseOrderId, String itemId);
}

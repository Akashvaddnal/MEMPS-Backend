package com.mepms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.mepms.entity.PurchaseOrder;

public interface PurchaseOrderService {
    List<PurchaseOrder> getAllPurchaseOrders();
    Optional<PurchaseOrder> getPurchaseOrderById(String id);
    Optional<PurchaseOrder> getPurchaseOrderByPoNumber(String poNumber);
    List<PurchaseOrder> getPurchaseOrdersByVendorId(String vendorId);
    List<PurchaseOrder> getPurchaseOrdersByStatus(String status);
    List<PurchaseOrder> getPurchaseOrdersByDateRange(Date start, Date end);
    PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder);
    PurchaseOrder updatePurchaseOrder(String id, PurchaseOrder purchaseOrder);
    void deletePurchaseOrder(String id);

    // Example report: total payable amount per vendor
    Double getTotalAmountByVendor(String vendorId);
}

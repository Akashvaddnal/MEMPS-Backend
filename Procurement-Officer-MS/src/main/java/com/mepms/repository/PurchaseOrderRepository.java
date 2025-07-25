package com.mepms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {
    Optional<PurchaseOrder> findByPoNumber(String poNumber);
    List<PurchaseOrder> findByVendorId(String vendorId);
    List<PurchaseOrder> findByStatus(String status);
    // For report: find by date range, vendor, etc. You can add custom methods
}

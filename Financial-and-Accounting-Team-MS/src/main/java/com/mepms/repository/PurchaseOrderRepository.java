package com.mepms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends MongoRepository<PurchaseOrder, String> {
	PurchaseOrder findByPoNumber(String poNumber);
    List<PurchaseOrder> findByStatus(String status);
    List<PurchaseOrder> findByRequestedBy(String requestedBy);
    List<PurchaseOrder> findByVendorId(String vendorId);
    List<PurchaseOrder> findByDateIssuedBetween(LocalDateTime from, LocalDateTime to);
    List<PurchaseOrder> findByPriority(String priority);
}

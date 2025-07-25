package com.mepms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.PurchaseOrderItem;

public interface PurchaseOrderItemRepository extends MongoRepository<PurchaseOrderItem, String> {
    List<PurchaseOrderItem> findByPoId(String poId);
    List<PurchaseOrderItem> findByEquipmentId(String equipmentId);
}

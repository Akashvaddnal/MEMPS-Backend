package com.mepms.service;

import java.util.List;
import java.util.Optional;

import com.mepms.entity.PurchaseOrderItem;

public interface PurchaseOrderItemService {
    List<PurchaseOrderItem> getAllItems();
    Optional<PurchaseOrderItem> getItemById(String id);
    List<PurchaseOrderItem> getItemsByPoId(String poId);
    List<PurchaseOrderItem> getItemsByEquipmentId(String equipmentId);
    PurchaseOrderItem createItem(PurchaseOrderItem item);
    PurchaseOrderItem updateItem(String id, PurchaseOrderItem item);
    void deleteItem(String id);
}

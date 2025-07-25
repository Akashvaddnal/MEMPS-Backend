package com.mepms.service;

import com.mepms.entity.InventoryAudit;
import java.util.List;
import java.util.Optional;

public interface InventoryAuditService {
    InventoryAudit save(InventoryAudit inventoryAudit);
    Optional<InventoryAudit> findById(String id);
    List<InventoryAudit> findAll();
    List<InventoryAudit> findByPerformedBy(String performedBy);
    void deleteById(String id);
}

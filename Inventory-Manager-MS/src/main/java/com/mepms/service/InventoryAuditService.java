//package com.mepms.service;
//
//import com.mepms.entity.InventoryAudit;
//import java.util.List;
//import java.util.Optional;
//
//public interface InventoryAuditService {
//    InventoryAudit save(InventoryAudit inventoryAudit);
//    Optional<InventoryAudit> findById(String id);
//    List<InventoryAudit> findAll();
//    List<InventoryAudit> findByPerformedBy(String performedBy);
//    void deleteById(String id);
//    InventoryAudit updateInventoryAudit(String id, InventoryAudit updatedAudit);
//}


package com.mepms.service;

import com.mepms.entity.InventoryAudit;
import java.util.List;

public interface InventoryAuditService {
    InventoryAudit create(InventoryAudit audit);
    InventoryAudit getById(String id);
    List<InventoryAudit> getAll();
    InventoryAudit update(String id, InventoryAudit audit);
    void delete(String id);
    List<InventoryAudit> findByPerformedBy(String performedBy);
}

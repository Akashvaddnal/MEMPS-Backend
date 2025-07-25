package com.mepms.repository;

import com.mepms.entity.InventoryAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventoryAuditRepository extends MongoRepository<InventoryAudit, String> {
    List<InventoryAudit> findByPerformedBy(String performedBy);
}

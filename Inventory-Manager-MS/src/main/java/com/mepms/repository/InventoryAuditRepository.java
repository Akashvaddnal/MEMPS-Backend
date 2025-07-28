//package com.mepms.repository;
//
//import com.mepms.entity.InventoryAudit;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface InventoryAuditRepository extends MongoRepository<InventoryAudit, String> {
//    List<InventoryAudit> findByPerformedBy(String performedBy);
//}


package com.mepms.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mepms.entity.InventoryAudit;

@Repository
public interface InventoryAuditRepository extends MongoRepository<InventoryAudit, String> {
    // Add custom queries if needed
	List<InventoryAudit> findByPerformedBy(String performedBy);
	
}

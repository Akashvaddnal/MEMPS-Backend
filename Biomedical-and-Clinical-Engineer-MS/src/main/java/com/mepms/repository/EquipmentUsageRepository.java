package com.mepms.repository;

import com.mepms.entity.EquipmentUsage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentUsageRepository extends MongoRepository<EquipmentUsage, String> {
    List<EquipmentUsage> findByEquipmentId(String equipmentId);
    List<EquipmentUsage> findByUsedBy(String usedBy);
    List<EquipmentUsage> findByReservedBy(String reservedBy);
}

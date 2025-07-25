package com.mepms.service;

import com.mepms.entity.EquipmentUsage;
import java.util.List;
import java.util.Optional;

public interface EquipmentUsageService {
    EquipmentUsage save(EquipmentUsage equipmentUsage);
    Optional<EquipmentUsage> findById(String id);
    List<EquipmentUsage> findAll();
    List<EquipmentUsage> findByEquipmentId(String equipmentId);
    List<EquipmentUsage> findByUsedBy(String usedBy);
    List<EquipmentUsage> findByReservedBy(String reservedBy);
    void deleteById(String id);
}

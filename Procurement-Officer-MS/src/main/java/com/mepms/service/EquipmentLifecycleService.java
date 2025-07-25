package com.mepms.service;

import java.util.Optional;

import com.mepms.entity.EquipmentLifecycle;

public interface EquipmentLifecycleService {
    EquipmentLifecycle createLifecycle(EquipmentLifecycle lifecycle);
    Optional<EquipmentLifecycle> getLifecycleById(String id);
    Optional<EquipmentLifecycle> getLifecycleByEquipmentId(String equipmentId);
    EquipmentLifecycle updateLifecycle(String id, EquipmentLifecycle lifecycle);
    void deleteLifecycle(String id);

    // Additional reporting/statistics like get total maintenance cost per equipment
    Double getTotalMaintenanceCostByEquipmentId(String equipmentId);
    Integer getMaintenanceCountByEquipmentId(String equipmentId);
}

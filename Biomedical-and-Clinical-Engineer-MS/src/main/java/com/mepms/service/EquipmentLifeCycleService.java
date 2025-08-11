package com.mepms.service;

import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.entity.EquipmentUsage;

import java.util.List;
import java.util.Optional;

public interface EquipmentLifeCycleService {
    EquipmentLifeCycle save(EquipmentLifeCycle equipmentLifeCycle);
    Optional<EquipmentLifeCycle> findById(String id);
    List<EquipmentLifeCycle> findAll();
    List<EquipmentLifeCycle> findByEquipmentId(String equipmentId);
    void deleteById(String id);  
    List<EquipmentUsage> findByUnitId(String unitId);
    EquipmentLifeCycle update(String id, EquipmentLifeCycle lifecycle);
   
}

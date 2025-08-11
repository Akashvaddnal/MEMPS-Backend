package com.mepms.service;


import java.util.List;
import java.util.Optional;

import com.mepms.entity.EquipmentUnit;

public interface EquipmentUnitService {

    EquipmentUnit createEquipmentUnit(EquipmentUnit equipmentUnit);

    Optional<EquipmentUnit> getEquipmentUnitById(String id);

    List<EquipmentUnit> getAllEquipmentUnits();

    EquipmentUnit updateEquipmentUnit(String id, EquipmentUnit equipmentUnit);

    void deleteEquipmentUnit(String id);

    List<EquipmentUnit> findByEquipmentUnitIdAndDepartmentId(String equipmentUnitId, String departmentId);
    List<EquipmentUnit> findByEquipmentUnitId(String equipmentUnitId);
    List<EquipmentUnit> findByDepartmentId(String departmentId);
    
 // EquipmentUnitService.java

    EquipmentUnit updateEquipmentUnitStatus(String id, String status);

    EquipmentUnit releaseEquipmentUnit(String id);

}

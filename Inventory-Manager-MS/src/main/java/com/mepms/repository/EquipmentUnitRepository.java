package com.mepms.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mepms.entity.EquipmentUnit;

public interface EquipmentUnitRepository extends MongoRepository<EquipmentUnit, String> {
	List<EquipmentUnit> findByEquipmentUnitId(String equipmentUnitId);
	List<EquipmentUnit> findByDepartmentId(String departmentId);
    List<EquipmentUnit> findByEquipmentUnitIdAndDepartmentId(String equipmentUnitId, String departmentId);
}

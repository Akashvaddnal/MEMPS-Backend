package com.mepms.repository;

import com.mepms.entity.EquipmentLifeCycle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentLifeCycleRepository extends MongoRepository<EquipmentLifeCycle, String> {
    List<EquipmentLifeCycle> findByEquipmentId(String equipmentId);
}

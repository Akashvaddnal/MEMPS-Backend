package com.mepms.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.EquipmentLifecycle;

public interface EquipmentLifecycleRepository extends MongoRepository<EquipmentLifecycle, String> {
    Optional<EquipmentLifecycle> findByEquipmentId(String equipmentId);
}

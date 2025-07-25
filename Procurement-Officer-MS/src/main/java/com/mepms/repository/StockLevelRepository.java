package com.mepms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import com.mepms.entity.StockLevel;

public interface StockLevelRepository extends MongoRepository<StockLevel, String> {
    List<StockLevel> findByEquipmentId(String equipmentId);
}

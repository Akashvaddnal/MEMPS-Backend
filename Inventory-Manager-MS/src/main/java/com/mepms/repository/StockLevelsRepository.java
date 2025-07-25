package com.mepms.repository;

import com.mepms.entity.StockLevels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockLevelsRepository extends MongoRepository<StockLevels, String> {
    List<StockLevels> findByEquipmentId(String equipmentId);
}

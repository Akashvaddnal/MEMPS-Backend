package com.mepms.service;

import com.mepms.entity.StockLevels;
import java.util.List;
import java.util.Optional;

public interface StockLevelsService {
    StockLevels save(StockLevels stockLevels);
    Optional<StockLevels> findById(String id);
    List<StockLevels> findAll();
    List<StockLevels> findByEquipmentId(String equipmentId);
    void deleteById(String id);
}

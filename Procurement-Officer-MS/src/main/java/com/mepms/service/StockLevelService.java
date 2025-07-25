package com.mepms.service;

import java.util.List;

import com.mepms.entity.StockLevel;

public interface StockLevelService {
    List<StockLevel> getAllStockLevels();
    List<StockLevel> getStockLevelsByEquipmentId(String equipmentId);
    StockLevel createStockLevel(StockLevel stockLevel);
    StockLevel updateStockLevel(String id, StockLevel stockLevel);
    void deleteStockLevel(String id);

    // Reporting / Alerts
    List<StockLevel> getStockLevelsBelowMinimum();
}

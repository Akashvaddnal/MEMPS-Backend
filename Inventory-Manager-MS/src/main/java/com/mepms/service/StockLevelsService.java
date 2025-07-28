//package com.mepms.service;
//
//import com.mepms.entity.StockLevels;
//import java.util.List;
//import java.util.Optional;
//
//public interface StockLevelsService {
//    StockLevels save(StockLevels stockLevels);
//    Optional<StockLevels> findById(String id);
//    List<StockLevels> findAll();
//    List<StockLevels> findByEquipmentId(String equipmentId);
//    void deleteById(String id);
//    StockLevels updateStockLevels(String id, StockLevels updatedStockLevels);
//}


package com.mepms.service;

import com.mepms.entity.StockLevels;
import java.util.List;

public interface StockLevelsService {
    StockLevels create(StockLevels stockLevel);
    StockLevels getById(String id);
    List<StockLevels> getAll();
    List<StockLevels> getByEquipmentId(String equipmentId);
    StockLevels update(String id, StockLevels stockLevel);
    void delete(String id);
}

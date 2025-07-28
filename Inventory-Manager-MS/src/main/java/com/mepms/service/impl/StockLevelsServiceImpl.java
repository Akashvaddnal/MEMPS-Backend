//package com.mepms.service.impl;
//
//import com.mepms.entity.StockLevels;
//import com.mepms.repository.StockLevelsRepository;
//import com.mepms.service.StockLevelsService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class StockLevelsServiceImpl implements StockLevelsService {
//    @Autowired
//    private StockLevelsRepository repository;
//
//    @Override
//    public StockLevels save(StockLevels stockLevels) {
//        return repository.save(stockLevels);
//    }
//
//    @Override
//    public Optional<StockLevels> findById(String id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public List<StockLevels> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public List<StockLevels> findByEquipmentId(String equipmentId) {
//        return repository.findByEquipmentId(equipmentId);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        repository.deleteById(id);
//    }
//    
//    @Override
//    public StockLevels updateStockLevels(String id, StockLevels updatedStockLevels) {
//        StockLevels stock = repository.findById(id).orElseThrow(() -> new RuntimeException("StockLevels not found with id: " + id));
//        updatedStockLevels.setId(id);
//        return repository.save(updatedStockLevels);
//    }
//
//}


package com.mepms.service.impl;

import com.mepms.entity.StockLevels;
import com.mepms.repository.StockLevelsRepository;
import com.mepms.service.StockLevelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockLevelsServiceImpl implements StockLevelsService {

    @Autowired
    private StockLevelsRepository repo;

    @Override
    public StockLevels create(StockLevels stockLevel) {
        return repo.save(stockLevel);
    }

    @Override
    public StockLevels getById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<StockLevels> getAll() {
        return repo.findAll();
    }

    @Override
    public StockLevels update(String id, StockLevels stockLevel) {
        stockLevel.setId(id);
        return repo.save(stockLevel);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
    
    @Override
  public List<StockLevels> getByEquipmentId(String equipmentId) {
      return repo.findByEquipmentId(equipmentId);
  }
}

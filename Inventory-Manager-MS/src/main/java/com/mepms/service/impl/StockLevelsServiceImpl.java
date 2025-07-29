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

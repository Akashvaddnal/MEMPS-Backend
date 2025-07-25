package com.mepms.service.impl;

import com.mepms.entity.StockLevels;
import com.mepms.repository.StockLevelsRepository;
import com.mepms.service.StockLevelsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockLevelsServiceImpl implements StockLevelsService {
    @Autowired
    private StockLevelsRepository repository;

    @Override
    public StockLevels save(StockLevels stockLevels) {
        return repository.save(stockLevels);
    }

    @Override
    public Optional<StockLevels> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<StockLevels> findAll() {
        return repository.findAll();
    }

    @Override
    public List<StockLevels> findByEquipmentId(String equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

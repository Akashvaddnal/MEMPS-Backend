package com.mepms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mepms.entity.StockLevel;
import com.mepms.repository.StockLevelRepository;
import com.mepms.service.StockLevelService;

@Service
public class StockLevelServiceImpl implements StockLevelService {

    private final StockLevelRepository stockLevelRepository;

    public StockLevelServiceImpl(StockLevelRepository stockLevelRepository) {
        this.stockLevelRepository = stockLevelRepository;
    }

    @Override
    public List<StockLevel> getAllStockLevels() {
        return stockLevelRepository.findAll();
    }

    @Override
    public List<StockLevel> getStockLevelsByEquipmentId(String equipmentId) {
        return stockLevelRepository.findByEquipmentId(equipmentId);
    }

    @Override
    public StockLevel createStockLevel(StockLevel stockLevel) {
        return stockLevelRepository.save(stockLevel);
    }

    @Override
    public StockLevel updateStockLevel(String id, StockLevel stockLevel) {
        stockLevel.setId(id);
        return stockLevelRepository.save(stockLevel);
    }

    @Override
    public void deleteStockLevel(String id) {
        stockLevelRepository.deleteById(id);
    }

//    @Override
//    public List<StockLevel> getStockLevelsBelowMinimum() {
//        return stockLevelRepository.findAll().stream()
//            .filter(s -> s.getCurrentQuantity() < s.getMinRequired())
//            .collect(Collectors.toList());
//    }
    @Override
    public List<StockLevel> getStockLevelsBelowMinimum() {
        return stockLevelRepository.findAll().stream()
            .filter(stock -> {
                Integer current = stock.getCurrentQuantity();
                Integer minimum = stock.getMinRequired();
                System.out.println("Current: " + current + ", Minimum: " + minimum);
                return current != null && minimum != null && current < minimum;
            })
            .collect(Collectors.toList());
    }
}

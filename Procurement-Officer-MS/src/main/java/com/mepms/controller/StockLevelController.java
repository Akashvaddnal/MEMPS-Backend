package com.mepms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.StockLevel;
import com.mepms.service.StockLevelService;

@RestController
@RequestMapping("/api/stock-levels")
@CrossOrigin(origins = "*")
public class StockLevelController {

    private final StockLevelService stockLevelService;

    public StockLevelController(StockLevelService stockLevelService) {
        this.stockLevelService = stockLevelService;
    }

    @GetMapping
    public List<StockLevel> getAll() {
        return stockLevelService.getAllStockLevels();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<StockLevel> getByEquipment(@PathVariable String equipmentId) {
        return stockLevelService.getStockLevelsByEquipmentId(equipmentId);
    }

    @PostMapping
    public StockLevel create(@RequestBody StockLevel stockLevel) {
        return stockLevelService.createStockLevel(stockLevel);
    }

    @PutMapping("/{id}")
    public StockLevel update(@PathVariable String id, @RequestBody StockLevel stockLevel) {
        return stockLevelService.updateStockLevel(id, stockLevel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        stockLevelService.deleteStockLevel(id);
    }

    @GetMapping("/alerts/below-minimum")
    public List<StockLevel> getBelowMinimum() {
        return stockLevelService.getStockLevelsBelowMinimum();
    }
}

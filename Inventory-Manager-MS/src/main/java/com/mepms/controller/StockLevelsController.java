package com.mepms.controller;

import com.mepms.entity.StockLevels;
import com.mepms.service.StockLevelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-levels")
public class StockLevelsController {
    @Autowired
    private StockLevelsService service;

    @PostMapping
    public ResponseEntity<StockLevels> create(@RequestBody StockLevels stockLevels) {
        return ResponseEntity.ok(service.create(stockLevels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockLevels> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
				
    }

    @GetMapping
    public List<StockLevels> getAll() {
        return service.getAll();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<StockLevels> getByEquipmentId(@PathVariable String equipmentId) {
        return service.getByEquipmentId(equipmentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/stock-levels/{id}")
    public StockLevels updateStockLevels(@PathVariable String id, @RequestBody StockLevels stock) {
        return service.update(id, stock);
    }
}

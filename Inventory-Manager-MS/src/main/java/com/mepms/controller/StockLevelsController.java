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
        return ResponseEntity.ok(service.save(stockLevels));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockLevels> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<StockLevels> getAll() {
        return service.findAll();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<StockLevels> getByEquipmentId(@PathVariable String equipmentId) {
        return service.findByEquipmentId(equipmentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

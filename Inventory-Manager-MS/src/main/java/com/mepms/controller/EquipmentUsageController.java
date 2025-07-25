package com.mepms.controller;

import com.mepms.entity.EquipmentUsage;
import com.mepms.service.EquipmentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-usage")
public class EquipmentUsageController {
    @Autowired
    private EquipmentUsageService service;

    @PostMapping
    public ResponseEntity<EquipmentUsage> create(@RequestBody EquipmentUsage equipmentUsage) {
        return ResponseEntity.ok(service.save(equipmentUsage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentUsage> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<EquipmentUsage> getAll() {
        return service.findAll();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<EquipmentUsage> getByEquipmentId(@PathVariable String equipmentId) {
        return service.findByEquipmentId(equipmentId);
    }

    @GetMapping("/usedby/{usedBy}")
    public List<EquipmentUsage> getByUsedBy(@PathVariable String usedBy) {
        return service.findByUsedBy(usedBy);
    }

    @GetMapping("/reservedby/{reservedBy}")
    public List<EquipmentUsage> getByReservedBy(@PathVariable String reservedBy) {
        return service.findByReservedBy(reservedBy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

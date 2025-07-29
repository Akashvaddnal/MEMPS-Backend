package com.mepms.controller;

import com.mepms.entity.Equipment;

import com.mepms.service.EquipmentService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
	private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
        
    }

    @GetMapping
    public List<Equipment> getAll() {
        return equipmentService.getAllEquipment();
    }

    @GetMapping("/{id}")
    public Optional<Equipment> getById(@PathVariable String id) {
        return equipmentService.getEquipmentById(id);
    }

    @GetMapping("/serial-number/{serialNumber}")
    public Equipment getBySerialNumber(@PathVariable String serialNumber) {
        return equipmentService.getEquipmentBySerialNumber(serialNumber);
    }

    @GetMapping("/category/{category}")
    public List<Equipment> getByCategory(@PathVariable String category) {
        return equipmentService.getEquipmentsByCategory(category);
    }

    @GetMapping("/status/{status}")
    public List<Equipment> getByStatus(@PathVariable String status) {
        return equipmentService.getEquipmentsByStatus(status);
    }

    @PostMapping
    public Equipment create(@RequestBody Equipment equipment) {
        return equipmentService.createEquipment(equipment);
    }

    @PutMapping("/{id}")
    public Equipment update(@PathVariable String id, @RequestBody Equipment equipment) {
        return equipmentService.updateEquipment(id, equipment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
		equipmentService.deleteEquipment(id);
		return ResponseEntity.noContent().build();
	}

}

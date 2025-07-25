package com.mepms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.Equipment;
import com.mepms.service.EquipmentService;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*")
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
    public Optional<Equipment> getBySerialNumber(@PathVariable String serialNumber) {
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

    @GetMapping("/vendor/{vendorId}")
    public List<Equipment> getByVendor(@PathVariable String vendorId) {
        return equipmentService.getEquipmentsByVendorId(vendorId);
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
    public void delete(@PathVariable String id) {
        equipmentService.deleteEquipment(id);
    }
}

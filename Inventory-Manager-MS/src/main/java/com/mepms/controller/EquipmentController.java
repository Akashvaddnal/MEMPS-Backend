package com.mepms.controller;

import com.mepms.entity.Equipment;
import com.mepms.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService service;

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(service.save(equipment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Equipment> getAll() {
        return service.findAll();
    }

    @GetMapping("/serial/{serialNumber}")
    public List<Equipment> getBySerialNumber(@PathVariable String serialNumber) {
        return service.findBySerialNumber(serialNumber);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Equipment> getByVendorId(@PathVariable String vendorId) {
        return service.findByVendorId(vendorId);
    }

    @GetMapping("/category/{category}")
    public List<Equipment> getByCategory(@PathVariable String category) {
        return service.findByCategory(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

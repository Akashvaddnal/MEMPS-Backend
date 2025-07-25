package com.mepms.controller;

import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.service.EquipmentLifeCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-lifecycle")
public class EquipmentLifeCycleController {
    @Autowired
    private EquipmentLifeCycleService service;

    @PostMapping
    public ResponseEntity<EquipmentLifeCycle> create(@RequestBody EquipmentLifeCycle equipmentLifeCycle) {
        return ResponseEntity.ok(service.save(equipmentLifeCycle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentLifeCycle> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<EquipmentLifeCycle> getAll() {
        return service.findAll();
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<EquipmentLifeCycle> getByEquipmentId(@PathVariable String equipmentId) {
        return service.findByEquipmentId(equipmentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.mepms.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.EquipmentLifecycle;
import com.mepms.service.EquipmentLifecycleService;

@RestController
@RequestMapping("/api/equipment-lifecycle")
@CrossOrigin(origins = "*")
public class EquipmentLifecycleController {

    private final EquipmentLifecycleService lifecycleService;

    public EquipmentLifecycleController(EquipmentLifecycleService lifecycleService) {
        this.lifecycleService = lifecycleService;
    }

    @PostMapping
    public EquipmentLifecycle create(@RequestBody EquipmentLifecycle lifecycle) {
        return lifecycleService.createLifecycle(lifecycle);
    }

    @GetMapping("/{id}")
    public Optional<EquipmentLifecycle> getById(@PathVariable String id) {
        return lifecycleService.getLifecycleById(id);
    }

    @GetMapping("/equipment/{equipmentId}")
    public Optional<EquipmentLifecycle> getByEquipment(@PathVariable String equipmentId) {
        return lifecycleService.getLifecycleByEquipmentId(equipmentId);
    }

    @PutMapping("/{id}")
    public EquipmentLifecycle update(@PathVariable String id, @RequestBody EquipmentLifecycle lifecycle) {
        return lifecycleService.updateLifecycle(id, lifecycle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        lifecycleService.deleteLifecycle(id);
    }

    @GetMapping("/reports/total-maintenance-cost/{equipmentId}")
    public Double getTotalMaintenanceCost(@PathVariable String equipmentId) {
        return lifecycleService.getTotalMaintenanceCostByEquipmentId(equipmentId);
    }

    @GetMapping("/reports/maintenance-count/{equipmentId}")
    public Integer getMaintenanceCount(@PathVariable String equipmentId) {
        return lifecycleService.getMaintenanceCountByEquipmentId(equipmentId);
    }
}

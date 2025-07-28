package com.mepms.controller;

import com.mepms.entity.InventoryAudit;
import com.mepms.service.InventoryAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-audit")
public class InventoryAuditController {
    @Autowired
    private InventoryAuditService service;

    @PostMapping
    public ResponseEntity<InventoryAudit> create(@RequestBody InventoryAudit inventoryAudit) {
        return ResponseEntity.ok(service.create(inventoryAudit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryAudit> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public List<InventoryAudit> getAll() {
        return service.getAll();
    }

    @GetMapping("/performedby/{performedBy}")
    public List<InventoryAudit> getByPerformedBy(@PathVariable String performedBy) {
        return service.findByPerformedBy(performedBy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/inventory-audit/{id}")
    public InventoryAudit updateInventoryAudit(@PathVariable String id, @RequestBody InventoryAudit audit) {
        return service.update(id, audit);
    }
}

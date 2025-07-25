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
        return ResponseEntity.ok(service.save(inventoryAudit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryAudit> getById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<InventoryAudit> getAll() {
        return service.findAll();
    }

    @GetMapping("/performedby/{performedBy}")
    public List<InventoryAudit> getByPerformedBy(@PathVariable String performedBy) {
        return service.findByPerformedBy(performedBy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

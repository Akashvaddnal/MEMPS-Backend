package com.mepms.service.impl;

import com.mepms.entity.InventoryAudit;
import com.mepms.repository.InventoryAuditRepository;
import com.mepms.service.InventoryAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryAuditServiceImpl implements InventoryAuditService {
    @Autowired
    private InventoryAuditRepository repository;

    @Override
    public InventoryAudit save(InventoryAudit inventoryAudit) {
        return repository.save(inventoryAudit);
    }

    @Override
    public Optional<InventoryAudit> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<InventoryAudit> findAll() {
        return repository.findAll();
    }

    @Override
    public List<InventoryAudit> findByPerformedBy(String performedBy) {
        return repository.findByPerformedBy(performedBy);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

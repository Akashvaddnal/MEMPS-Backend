package com.mepms.service.impl;

import com.mepms.entity.InventoryAudit;
import com.mepms.repository.InventoryAuditRepository;
import com.mepms.service.InventoryAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryAuditServiceImpl implements InventoryAuditService {

    @Autowired
    private InventoryAuditRepository repo;

    @Override
    public InventoryAudit create(InventoryAudit audit) {
        return repo.save(audit);
    }

    @Override
    public InventoryAudit getById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<InventoryAudit> getAll() {
        return repo.findAll();
    }

    @Override
    public InventoryAudit update(String id, InventoryAudit audit) {
        audit.setId(id);
        return repo.save(audit);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
    
    @Override
  public List<InventoryAudit> findByPerformedBy(String performedBy) {
      return repo.findByPerformedBy(performedBy);
  }
}


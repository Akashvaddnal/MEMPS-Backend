//package com.mepms.service.impl;
//
//import com.mepms.entity.InventoryAudit;
//import com.mepms.repository.InventoryAuditRepository;
//import com.mepms.service.InventoryAuditService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class InventoryAuditServiceImpl implements InventoryAuditService {
//    @Autowired
//    private InventoryAuditRepository repository;
//
//    @Override
//    public InventoryAudit save(InventoryAudit inventoryAudit) {
//        return repository.save(inventoryAudit);
//    }
//
//    @Override
//    public Optional<InventoryAudit> findById(String id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public List<InventoryAudit> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public List<InventoryAudit> findByPerformedBy(String performedBy) {
//        return repository.findByPerformedBy(performedBy);
//    }
//
//    @Override
//    public void deleteById(String id) {
//        repository.deleteById(id);
//    }
//    
//    @Override
//    public InventoryAudit updateInventoryAudit(String id, InventoryAudit updatedAudit) {
//        InventoryAudit audit = repository.findById(id).orElseThrow(() -> new RuntimeException("InventoryAudit not found with id: " + id));
//        updatedAudit.setId(id);
//        return repository.save(updatedAudit);
//    }
//
//}


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


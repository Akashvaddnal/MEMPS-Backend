package com.mepms.service.impl;

import com.mepms.entity.EquipmentUsage;
import com.mepms.repository.EquipmentUsageRepository;
import com.mepms.service.EquipmentUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentUsageServiceImpl implements EquipmentUsageService {
    @Autowired
    private EquipmentUsageRepository repository;

    @Override
    public EquipmentUsage save(EquipmentUsage equipmentUsage) {
        return repository.save(equipmentUsage);
    }

    @Override
    public Optional<EquipmentUsage> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<EquipmentUsage> findAll() {
        return repository.findAll();
    }

    @Override
    public List<EquipmentUsage> findByEquipmentId(String equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }

    @Override
    public List<EquipmentUsage> findByUsedBy(String usedBy) {
        return repository.findByUsedBy(usedBy);
    }

    @Override
    public List<EquipmentUsage> findByReservedBy(String reservedBy) {
        return repository.findByReservedBy(reservedBy);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
    @Override
    public EquipmentUsage updateEquipmentUsage(String id, EquipmentUsage updatedUsage) {
        EquipmentUsage usage = repository.findById(id).orElseThrow(() -> new RuntimeException("EquipmentUsage not found with id: " + id));
        updatedUsage.setId(id);
        return repository.save(updatedUsage);
    }

}

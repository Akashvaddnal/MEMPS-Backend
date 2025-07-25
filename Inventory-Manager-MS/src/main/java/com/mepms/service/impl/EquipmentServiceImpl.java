package com.mepms.service.impl;

import com.mepms.entity.Equipment;
import com.mepms.repository.EquipmentRepository;
import com.mepms.service.EquipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentRepository repository;

    @Override
    public Equipment save(Equipment equipment) {
        return repository.save(equipment);
    }

    @Override
    public Optional<Equipment> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Equipment> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Equipment> findBySerialNumber(String serialNumber) {
        return repository.findBySerialNumber(serialNumber);
    }

    @Override
    public List<Equipment> findByVendorId(String vendorId) {
        return repository.findByVendorId(vendorId);
    }

    @Override
    public List<Equipment> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

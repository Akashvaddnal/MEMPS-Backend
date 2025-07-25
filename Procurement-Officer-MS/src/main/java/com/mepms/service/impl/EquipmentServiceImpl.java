package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mepms.entity.Equipment;
import com.mepms.repository.EquipmentRepository;
import com.mepms.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Optional<Equipment> getEquipmentById(String id) {
        return equipmentRepository.findById(id);
    }

    @Override
    public Optional<Equipment> getEquipmentBySerialNumber(String serialNumber) {
        return equipmentRepository.findBySerialNumber(serialNumber);
    }

    @Override
    public List<Equipment> getEquipmentsByCategory(String category) {
        return equipmentRepository.findByCategory(category);
    }

    @Override
    public List<Equipment> getEquipmentsByStatus(String status) {
        return equipmentRepository.findByStatus(status);
    }

    @Override
    public List<Equipment> getEquipmentsByVendorId(String vendorId) {
        return equipmentRepository.findByVendorId(vendorId);
    }

    @Override
    public Equipment createEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Equipment updateEquipment(String id, Equipment equipment) {
        equipment.setId(id);
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipment(String id) {
        equipmentRepository.deleteById(id);
    }
}

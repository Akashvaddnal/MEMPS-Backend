package com.mepms.service;

import java.util.List;
import java.util.Optional;

import com.mepms.entity.Equipment;

public interface EquipmentService {
    List<Equipment> getAllEquipment();
    Optional<Equipment> getEquipmentById(String id);
    Optional<Equipment> getEquipmentBySerialNumber(String serialNumber);
    List<Equipment> getEquipmentsByCategory(String category);
    List<Equipment> getEquipmentsByStatus(String status);
    List<Equipment> getEquipmentsByVendorId(String vendorId);
    Equipment createEquipment(Equipment equipment);
    Equipment updateEquipment(String id, Equipment equipment);
    void deleteEquipment(String id);

    // Additional reporting methods if needed
}

package com.mepms.service;

import com.mepms.entity.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentService { 
    List<Equipment> getAllEquipment();
    Optional<Equipment> getEquipmentById(String id);
    Equipment getEquipmentBySerialNumber(String serialNumber);
    List<Equipment> getEquipmentsByCategory(String category);
    List<Equipment> getEquipmentsByStatus(String status);
//    List<Equipment> getEquipmentsByVendorId(String vendorId);
    Equipment createEquipment(Equipment equipment);
    Equipment updateEquipment(String id, Equipment equipment);
    void deleteEquipment(String id);
    List<Equipment> getEquipmentsByIds(List<String> equipmentIds);
}

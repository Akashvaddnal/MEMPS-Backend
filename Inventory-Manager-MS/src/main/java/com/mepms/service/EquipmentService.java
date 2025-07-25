package com.mepms.service;

import com.mepms.entity.Equipment;
import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    Equipment save(Equipment equipment);
    Optional<Equipment> findById(String id);
    List<Equipment> findAll();
    List<Equipment> findBySerialNumber(String serialNumber);
    List<Equipment> findByVendorId(String vendorId);
    List<Equipment> findByCategory(String category);
    void deleteById(String id);
}

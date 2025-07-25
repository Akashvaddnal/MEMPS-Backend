package com.mepms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.mepms.entity.Equipment;

public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    Optional<Equipment> findBySerialNumber(String serialNumber);
    List<Equipment> findByCategory(String category);
    List<Equipment> findByStatus(String status);
    List<Equipment> findByVendorId(String vendorId);
}

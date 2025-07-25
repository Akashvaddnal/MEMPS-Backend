package com.mepms.repository;

import com.mepms.entity.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {
    List<Equipment> findBySerialNumber(String serialNumber);
    List<Equipment> findByVendorId(String vendorId);
    List<Equipment> findByCategory(String category);
}

package com.mepms.service.impl;

import com.mepms.entity.Equipment;

import com.mepms.repository.EquipmentRepository;
import com.mepms.service.EquipmentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
	    public Equipment getEquipmentBySerialNumber(String serialNumber) {
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

//	    @Override
//	    public List<Equipment> getEquipmentsByVendorId(String vendorId) {
//	        return equipmentRepository.findByVendorId(vendorId);
//	    }
	    
//	    @Override
//	    public List<Equipment> getEquipmentsByVendorId(String vendorId) {
//	        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//	        if (vendor.isPresent() && vendor.get().getEquipmentProvided() != null) {
//	            return equipmentRepository.findBy_idIn(vendor.get().getEquipmentProvided());
//	        }
//	        return List.of();
//	    }
	    
	    @Override
	    public List<Equipment> getEquipmentsByIds(List<String> equipmentIds) {
	        return equipmentRepository.findByIdIn(equipmentIds);
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

//	    @Override
//	    public void deleteEquipment(String id) {
//	        equipmentRepository.deleteById(id);
//	    }
	    
	    
//	    @Override
//	    public void deleteEquipment(String id) {
//	        equipmentRepository.deleteById(id);
//	        // Remove this equipment from all vendors
//	        List<Vendor> vendors = vendorRepository.findAll();
//	        vendors.forEach(vendor -> {
//	            if (vendor.getEquipmentProvided() != null && vendor.getEquipmentProvided().contains(id)) {
//	                vendor.getEquipmentProvided().remove(id);
//	                vendorRepository.save(vendor);
//	            }
//	        });
//	    }
}

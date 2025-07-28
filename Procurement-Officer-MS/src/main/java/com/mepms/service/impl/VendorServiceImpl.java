package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mepms.entity.Equipment;
import com.mepms.entity.Vendor;
import com.mepms.repository.EquipmentRepository;
import com.mepms.repository.VendorRepository;
import com.mepms.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    
    @Autowired
    private EquipmentRepository equipmentRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Optional<Vendor> getVendorById(String id) {
        return vendorRepository.findById(id);
    }

    @Override
    public Optional<Vendor> getVendorByEmail(String email) {
        return vendorRepository.findByEmail(email);
    }

    @Override
    public Optional<Vendor> getVendorByName(String name) {
        return vendorRepository.findByName(name);
    }

    @Override
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor updateVendor(String id, Vendor vendor) {
        vendor.set_id(id);
        return vendorRepository.save(vendor);
    }

    @Override
    public void deleteVendor(String id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public long countVendors() {
        return vendorRepository.count();
    }
    
    @Override
    public Vendor addEquipmentToVendor(String vendorId, String equipmentId) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);
        
        if (vendorOpt.isPresent() && equipmentOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            if (vendor.getEquipmentProvided() == null) {
                vendor.setEquipmentProvided(List.of(equipmentId));
            } else if (!vendor.getEquipmentProvided().contains(equipmentId)) {
                vendor.getEquipmentProvided().add(equipmentId);
            }
            return vendorRepository.save(vendor);
        }
        return null;
    }

    @Override
    public Vendor removeEquipmentFromVendor(String vendorId, String equipmentId) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (vendorOpt.isPresent()) {
            Vendor vendor = vendorOpt.get();
            if (vendor.getEquipmentProvided() != null) {
                vendor.getEquipmentProvided().remove(equipmentId);
                return vendorRepository.save(vendor);
            }
        }
        return null;
    }

    @Override
    public List<Equipment> getVendorEquipment(String vendorId) {
        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
        if (vendor.isPresent() && vendor.get().getEquipmentProvided() != null) {
            return equipmentRepository.findBy_idIn(vendor.get().getEquipmentProvided());
        }
        return List.of();
    }
}

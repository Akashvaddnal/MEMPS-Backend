package com.mepms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mepms.entity.Vendor;
import com.mepms.repository.VendorRepository;
import com.mepms.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

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
        vendor.setId(id);
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
}

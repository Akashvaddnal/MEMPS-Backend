package com.mepms.service;

import java.util.List;
import java.util.Optional;

import com.mepms.entity.Vendor;

public interface VendorService {
    List<Vendor> getAllVendors();
    Optional<Vendor> getVendorById(String id);
    Optional<Vendor> getVendorByEmail(String email);
    Optional<Vendor> getVendorByName(String name);
    Vendor createVendor(Vendor vendor);
    Vendor updateVendor(String id, Vendor vendor);
    void deleteVendor(String id);

    // Reporting functions
    long countVendors();
}

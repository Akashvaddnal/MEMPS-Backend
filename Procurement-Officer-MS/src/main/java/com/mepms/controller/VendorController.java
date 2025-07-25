package com.mepms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.mepms.entity.Vendor;
import com.mepms.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{id}")
    public Optional<Vendor> getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/email/{email}")
    public Optional<Vendor> getVendorByEmail(@PathVariable String email) {
        return vendorService.getVendorByEmail(email);
    }

    @GetMapping("/name/{name}")
    public Optional<Vendor> getVendorByName(@PathVariable String name) {
        return vendorService.getVendorByName(name);
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    @PutMapping("/{id}")
    public Vendor updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
        return vendorService.updateVendor(id, vendor);
    }

    @DeleteMapping("/{id}")
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendor(id);
    }

    // Reporting endpoint example
    @GetMapping("/count")
    public long countVendors() {
        return vendorService.countVendors();
    }
}

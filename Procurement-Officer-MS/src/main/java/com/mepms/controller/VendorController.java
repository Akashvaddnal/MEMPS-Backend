package com.mepms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mepms.entity.Equipment;
import com.mepms.entity.Vendor;
import com.mepms.service.EquipmentService;
import com.mepms.service.VendorService;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

	 private final EquipmentService equipmentService;
    private final VendorService vendorService;

    public VendorController(EquipmentService equipmentService, VendorService vendorService) {
        this.equipmentService = equipmentService;
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

//    @GetMapping("/{vendorId}/equipment")
//    public List<Equipment> getVendorEquipment(@PathVariable String vendorId) {
//        return vendorService.getVendorEquipment(vendorId);
//    }

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

    @GetMapping("/count")
    public long countVendors() {
        return vendorService.countVendors();
    }

    @PostMapping("/{vendorId}/add-equipment/{equipmentId}")
    public Vendor addEquipmentToVendor(@PathVariable String vendorId, @PathVariable String equipmentId) {
        return vendorService.addEquipmentToVendor(vendorId, equipmentId);
    }

    @PostMapping("/{vendorId}/remove-equipment/{equipmentId}")
    public Vendor removeEquipmentFromVendor(@PathVariable String vendorId, @PathVariable String equipmentId) {
        return vendorService.removeEquipmentFromVendor(vendorId, equipmentId);
    }
    
    @GetMapping("/{vendorId}/equipment")
    public ResponseEntity<List<Equipment>> getVendorEquipment(@PathVariable String vendorId) {
        try {
            List<Equipment> equipment = equipmentService.getEquipmentsByVendorId(vendorId);
            return ResponseEntity.ok(equipment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Alternative version that returns both vendor and equipment details
     */
    @GetMapping("/{vendorId}/equipment-details")
    public ResponseEntity<Map<String, Object>> getVendorWithEquipmentDetails(@PathVariable String vendorId) {
        try {
            Optional<Vendor> vendor = vendorService.getVendorById(vendorId);
            if (vendor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            List<Equipment> equipment = equipmentService.getEquipmentsByVendorId(vendorId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("vendor", vendor.get());
            response.put("equipment", equipment);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
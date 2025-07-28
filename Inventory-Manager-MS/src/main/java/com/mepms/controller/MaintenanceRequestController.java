package com.mepms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mepms.entity.MaintenanceRequest;
import com.mepms.service.MaintenanceRequestService;

@RestController
@RequestMapping("/api/Main-Req")
public class MaintenanceRequestController {
	@Autowired
    private MaintenanceRequestService maintenanceRequestService;
	
	// Maintenance Requests endpoints
    @PostMapping("/maintenance")
    public MaintenanceRequest createMaintenance(@RequestBody MaintenanceRequest request) {
        return maintenanceRequestService.create(request);
    }

    @GetMapping("/maintenance/{id}")
    public MaintenanceRequest getMaintenance(@PathVariable String id) {
        return maintenanceRequestService.getById(id);
    }

    @GetMapping("/maintenance")
    public List<MaintenanceRequest> getAllMaintenances() {
        return maintenanceRequestService.getAll();
    }

    @PutMapping("/maintenance/{id}")
    public MaintenanceRequest updateMaintenance(@PathVariable String id, @RequestBody MaintenanceRequest request) {
        return maintenanceRequestService.update(id, request);
    }

    @DeleteMapping("/maintenance/{id}")
    public void deleteMaintenance(@PathVariable String id) {
        maintenanceRequestService.delete(id);
    }
}

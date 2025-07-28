package com.mepms.service;

import java.util.List;

import com.mepms.entity.MaintenanceRequest;

public interface MaintenanceRequestService {
    MaintenanceRequest create(MaintenanceRequest request);
    MaintenanceRequest getById(String id);
    List<MaintenanceRequest> getAll();
    MaintenanceRequest update(String id, MaintenanceRequest request);
    void delete(String id);
}

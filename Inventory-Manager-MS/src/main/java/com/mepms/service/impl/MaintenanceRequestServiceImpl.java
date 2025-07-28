package com.mepms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mepms.entity.MaintenanceRequest;
import com.mepms.repository.MaintenanceRequestRepository;
import com.mepms.service.MaintenanceRequestService;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService {
    @Autowired
    private MaintenanceRequestRepository repository;

    @Override
    public MaintenanceRequest create(MaintenanceRequest request) {
        return repository.save(request);
    }

    @Override
    public MaintenanceRequest getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<MaintenanceRequest> getAll() {
        return repository.findAll();
    }

    @Override
    public MaintenanceRequest update(String id, MaintenanceRequest request) {
        request.setId(id);
        return repository.save(request);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

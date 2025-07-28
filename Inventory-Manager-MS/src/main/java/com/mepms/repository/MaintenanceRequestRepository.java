package com.mepms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mepms.entity.MaintenanceRequest;

public interface MaintenanceRequestRepository extends MongoRepository<MaintenanceRequest, String> {
    // Add custom queries if needed
}

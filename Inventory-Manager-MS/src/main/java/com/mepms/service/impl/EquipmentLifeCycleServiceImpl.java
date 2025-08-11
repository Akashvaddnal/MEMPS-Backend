package com.mepms.service.impl;

import com.mepms.entity.EquipmentLifeCycle;
import com.mepms.repository.EquipmentLifeCycleRepository;
import com.mepms.service.EquipmentLifeCycleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentLifeCycleServiceImpl implements EquipmentLifeCycleService {
    @Autowired
    private EquipmentLifeCycleRepository repository;

    @Override
    public EquipmentLifeCycle save(EquipmentLifeCycle equipmentLifeCycle) {
        return repository.save(equipmentLifeCycle);
    }

    @Override
    public Optional<EquipmentLifeCycle> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<EquipmentLifeCycle> findAll() {
        return repository.findAll();
    }

    @Override
    public List<EquipmentLifeCycle> findByEquipmentId(String equipmentId) {
        return repository.findByEquipmentId(equipmentId);
    }
    
//    @Override
//    public EquipmentLifeCycle update(String id, EquipmentLifeCycle lifecycle) {
//        lifecycle.setId(id);
//        return repository.save(lifecycle);
//    }
    
    @Override
    public EquipmentLifeCycle update(String id, EquipmentLifeCycle lifecycle) {
        EquipmentLifeCycle existingLifeCycle = findById(id)
                .orElseThrow(() -> new RuntimeException("EquipmentLifeCycle not found with id: " + id));

        // Set id explicitly in existingLifeCycle (should already be there, but just in case)
        lifecycle.setId(id);

        // Update non-null (or non-zero for primitives) fields - customize as needed

        if (lifecycle.getEquipmentId() != null) {
            existingLifeCycle.setEquipmentId(lifecycle.getEquipmentId());
        }

        if (lifecycle.getUnitId() != null) {
            existingLifeCycle.setUnitId(lifecycle.getUnitId());
        }

        if (lifecycle.getAcquisitionDate() != null) {
            existingLifeCycle.setAcquisitionDate(lifecycle.getAcquisitionDate());
        }

        if (lifecycle.getExpectedEndOfLife() != null) {
            existingLifeCycle.setExpectedEndOfLife(lifecycle.getExpectedEndOfLife());
        }

        // For primitive fields, check if value is meaningful before updating
        if (lifecycle.getMaintenanceCount() != 0) {
            existingLifeCycle.setMaintenanceCount(lifecycle.getMaintenanceCount());
        }

        if (lifecycle.getTotalMaintenanceCost() != 0.0) {
            existingLifeCycle.setTotalMaintenanceCost(lifecycle.getTotalMaintenanceCost());
        }

        if (lifecycle.getWarrantyExpirationDate() != null) {
            existingLifeCycle.setWarrantyExpirationDate(lifecycle.getWarrantyExpirationDate());
        }

        if (lifecycle.getLastMaintenanceDate() != null) {
            existingLifeCycle.setLastMaintenanceDate(lifecycle.getLastMaintenanceDate());
        }

        if (lifecycle.getNextMaintenanceDate() != null) {
            existingLifeCycle.setNextMaintenanceDate(lifecycle.getNextMaintenanceDate());
        }

        if (lifecycle.getMainteneceDoneBy() != null) {
            existingLifeCycle.setMainteneceDoneBy(lifecycle.getMainteneceDoneBy());
        }

        if (lifecycle.getStatus() != null) {
            existingLifeCycle.setStatus(lifecycle.getStatus());
        }

        return repository.save(existingLifeCycle);
    }


    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

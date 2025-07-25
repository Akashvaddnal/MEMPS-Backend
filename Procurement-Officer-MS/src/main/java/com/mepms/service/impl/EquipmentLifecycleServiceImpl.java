package com.mepms.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mepms.entity.EquipmentLifecycle;
import com.mepms.repository.EquipmentLifecycleRepository;
import com.mepms.service.EquipmentLifecycleService;

@Service
public class EquipmentLifecycleServiceImpl implements EquipmentLifecycleService {

    private final EquipmentLifecycleRepository lifecycleRepository;

    public EquipmentLifecycleServiceImpl(EquipmentLifecycleRepository lifecycleRepository) {
        this.lifecycleRepository = lifecycleRepository;
    }

    @Override
    public EquipmentLifecycle createLifecycle(EquipmentLifecycle lifecycle) {
        return lifecycleRepository.save(lifecycle);
    }

    @Override
    public Optional<EquipmentLifecycle> getLifecycleById(String id) {
        return lifecycleRepository.findById(id);
    }

    @Override
    public Optional<EquipmentLifecycle> getLifecycleByEquipmentId(String equipmentId) {
        return lifecycleRepository.findByEquipmentId(equipmentId);
    }

    @Override
    public EquipmentLifecycle updateLifecycle(String id, EquipmentLifecycle lifecycle) {
        lifecycle.setId(id);
        return lifecycleRepository.save(lifecycle);
    }

    @Override
    public void deleteLifecycle(String id) {
        lifecycleRepository.deleteById(id);
    }

    @Override
    public Double getTotalMaintenanceCostByEquipmentId(String equipmentId) {
        return lifecycleRepository.findByEquipmentId(equipmentId)
                .map(EquipmentLifecycle::getTotalMaintenanceCost)
                .orElse(0.0);
    }

    @Override
    public Integer getMaintenanceCountByEquipmentId(String equipmentId) {
        return lifecycleRepository.findByEquipmentId(equipmentId)
                .map(EquipmentLifecycle::getMaintenanceCount)
                .orElse(0);
    }
}

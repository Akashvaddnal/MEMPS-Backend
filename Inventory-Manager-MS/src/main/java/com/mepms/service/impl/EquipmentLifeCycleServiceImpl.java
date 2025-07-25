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

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

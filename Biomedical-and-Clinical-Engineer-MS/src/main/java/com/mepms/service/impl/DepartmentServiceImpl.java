package com.mepms.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mepms.entity.Department;
import com.mepms.entity.Equipment;
import com.mepms.repository.DepartmentRepository;
import com.mepms.repository.EquipmentRepository;
import com.mepms.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EquipmentRepository equipmentRepository;

    // Id of Inventory Department - configure or retrieve from config/env ideally
    private static final String INVENTORY_DEPT_ID = "dept_inventory";

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EquipmentRepository equipmentRepository) {
        this.departmentRepository = departmentRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getById(String id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department createDepartment(Department department) {
        department.setCreatedAt(Instant.now());
        department.setUpdatedAt(Instant.now());
        if (department.getEquipmentInventory() == null) {
            department.setEquipmentInventory(new HashMap<>());
        }
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(String id, Department department) {
        return departmentRepository.findById(id).map(dep -> {
            dep.setName(department.getName());
            dep.setLocation(department.getLocation());
            dep.setContactPerson(department.getContactPerson());
            dep.setPhone(department.getPhone());
            dep.setEmail(department.getEmail());
            dep.setUpdatedAt(Instant.now());
            // Not updating equipmentInventory here for safety, use assignEquipment API
            return departmentRepository.save(dep);
        }).orElseThrow(() -> new NoSuchElementException("Department not found"));
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department assignEquipmentToDepartment(String deptId, String equipmentId) throws Exception {
        // Load department
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));

        // Load inventory department
        Department inventoryDept = departmentRepository.findById(INVENTORY_DEPT_ID)
                .orElseThrow(() -> new NoSuchElementException("Inventory department not found"));

        // Load Equipment
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new NoSuchElementException("Equipment not found"));

        // Check inventory stock for this equipment in inventory department
        Integer currentInventoryCount = inventoryDept.getEquipmentInventory() == null ? 0 :
                inventoryDept.getEquipmentInventory().getOrDefault(equipmentId, 0);

        if (currentInventoryCount <= 0) {
            throw new IllegalStateException("Equipment not available in inventory");
        }

        // Deduct from inventory department
        inventoryDept.getEquipmentInventory().put(equipmentId, currentInventoryCount - 1);
        
        // Optionally update equipment stock field as well
        equipment.setCurrentStock(equipment.getCurrentStock() - 1);
        equipmentRepository.save(equipment);
        
        departmentRepository.save(inventoryDept);

        // Add or increment count in department's equipmentInventory
        Map<String, Integer> deptEquipInventory = department.getEquipmentInventory();
        if (deptEquipInventory == null) {
            deptEquipInventory = new HashMap<>();
            department.setEquipmentInventory(deptEquipInventory);
        }

        deptEquipInventory.put(equipmentId, deptEquipInventory.getOrDefault(equipmentId, 0) + 1);
        department.setUpdatedAt(Instant.now());

        return departmentRepository.save(department);
    }

    @Override
    public List<Department> searchDepartments(String name, String location) {
        if (name != null && location != null) {
            // Simple example: filtering manually, you can create custom repo methods or use Mongo queries
            List<Department> byName = departmentRepository.findByNameContainingIgnoreCase(name);
            List<Department> byLocation = departmentRepository.findByLocationContainingIgnoreCase(location);

            // intersection
            Set<Department> byNameSet = new HashSet<>(byName);
            byNameSet.retainAll(byLocation);
            return new ArrayList<>(byNameSet);
        } else if (name != null) {
            return departmentRepository.findByNameContainingIgnoreCase(name);
        } else if (location != null) {
            return departmentRepository.findByLocationContainingIgnoreCase(location);
        } else {
            return departmentRepository.findAll();
        }
    }
    
    @Override
    public List<Equipment> getEquipmentsByDepartmentName(String departmentName) {
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if (departmentOpt.isEmpty()) {
            throw new NoSuchElementException("Department not found");
        }
        Department dept = departmentOpt.get();
        if (dept.getEquipmentInventory() == null || dept.getEquipmentInventory().isEmpty()) {
            return new ArrayList<>();
        }
        List<String> equipmentIds = new ArrayList<>(dept.getEquipmentInventory().keySet());
        return equipmentRepository.findByIdIn(equipmentIds);
    }
}

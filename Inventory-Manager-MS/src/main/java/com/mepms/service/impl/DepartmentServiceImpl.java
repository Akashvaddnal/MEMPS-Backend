//package com.mepms.service.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//import java.util.Set;
//
//import org.springframework.stereotype.Service;
//
//import com.mepms.entity.Department;
//import com.mepms.entity.Equipment;
//import com.mepms.repository.DepartmentRepository;
//import com.mepms.repository.EquipmentRepository;
//import com.mepms.service.DepartmentService;
//
//@Service
//public class DepartmentServiceImpl implements DepartmentService {
//
//    private final DepartmentRepository departmentRepository;
//    private final EquipmentRepository equipmentRepository;
//
//    // Id of Inventory Department - configure or retrieve from config/env ideally
//    private static final String INVENTORY_DEPT_ID = "dept_inventory";
//
//    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EquipmentRepository equipmentRepository) {
//        this.departmentRepository = departmentRepository;
//        this.equipmentRepository = equipmentRepository;
//    }
//
//    @Override
//    public List<Department> getAllDepartments() {
//        return departmentRepository.findAll();
//    }
//
//    @Override
//    public Optional<Department> getById(String id) {
//        return departmentRepository.findById(id);
//    }
//
//    @Override
//    public Department createDepartment(Department department) {
//        department.setCreatedAt(new Date());
//        department.setUpdatedAt(new Date());
//        if (department.getEquipmentInventory() == null) {
//            department.setEquipmentInventory(new HashMap<>());
//        }
//        return departmentRepository.save(department);
//    }
//
////    @Override
////    public Department updateDepartment(String id, Department department) {
////        return departmentRepository.findById(id).map(dep -> {
////            dep.setName(department.getName());
////            dep.setLocation(department.getLocation());
////            dep.setContactPerson(department.getContactPerson());
////            dep.setPhone(department.getPhone());
////            dep.setEmail(department.getEmail());
////            dep.setUpdatedAt(new Date());
////            dep.setImage(department.getImage());
////            // Not updating equipmentInventory here for safety, use assignEquipment API
////            return departmentRepository.save(dep);
////        }).orElseThrow(() -> new NoSuchElementException("Department not found"));
////    }
//    
//    @Override
//    public Department updateDepartment(String id, Department department) {
//        return departmentRepository.findById(id).map(dep -> {
//            dep.setName(department.getName());
//            dep.setLocation(department.getLocation());
//            dep.setContactPerson(department.getContactPerson());
//            dep.setPhone(department.getPhone());
//            dep.setEmail(department.getEmail());
//            dep.setUpdatedAt(new Date());
//            dep.setImage(department.getImage());
//            if(department.getEquipmentInventory() != null) {
//                Map<String, Integer> newInventory = department.getEquipmentInventory();
//                Map<String, Integer> currentInventory = dep.getEquipmentInventory();
//                if(currentInventory == null){
//                    dep.setEquipmentInventory(newInventory);
//                } else {
//                    for(Map.Entry<String, Integer> entry : newInventory.entrySet()){
//                        String equipmentId = entry.getKey();
//                        Integer newCount = entry.getValue();
//                        Integer existingCount = currentInventory.getOrDefault(equipmentId, 0);
//                        currentInventory.put(equipmentId, existingCount + newCount);
//                    }
//                    dep.setEquipmentInventory(currentInventory);
//                }
//            }
//            return departmentRepository.save(dep);
//        }).orElseThrow(() -> new NoSuchElementException("Department not found"));
//    }
//
//
//    @Override
//    public void deleteDepartment(String id) {
//        departmentRepository.deleteById(id);
//    }
//
//    @Override
//    public Department assignEquipmentToDepartment(String deptId, String equipmentId) throws Exception {
//        // Load department
//        Department department = departmentRepository.findById(deptId)
//                .orElseThrow(() -> new NoSuchElementException("Department not found"));
//
//        // Load inventory department
//        Department inventoryDept = departmentRepository.findById(INVENTORY_DEPT_ID)
//                .orElseThrow(() -> new NoSuchElementException("Inventory department not found"));
//
//        // Load Equipment
//        Equipment equipment = equipmentRepository.findById(equipmentId)
//                .orElseThrow(() -> new NoSuchElementException("Equipment not found"));
//
//        // Check inventory stock for this equipment in inventory department
//        Integer currentInventoryCount = inventoryDept.getEquipmentInventory() == null ? 0 :
//                inventoryDept.getEquipmentInventory().getOrDefault(equipmentId, 0);
//
//        if (currentInventoryCount <= 0) {
//            throw new IllegalStateException("Equipment not available in inventory");
//        }
//
//        // Deduct from inventory department
//        inventoryDept.getEquipmentInventory().put(equipmentId, currentInventoryCount - 1);
//        
//        // Optionally update equipment stock field as well
//        equipment.setCurrentStock(equipment.getCurrentStock() - 1);
//        equipmentRepository.save(equipment);
//        
//        departmentRepository.save(inventoryDept);
//
//        // Add or increment count in department's equipmentInventory
//        Map<String, Integer> deptEquipInventory = department.getEquipmentInventory();
//        if (deptEquipInventory == null) {
//            deptEquipInventory = new HashMap<>();
//            department.setEquipmentInventory(deptEquipInventory);
//        }
//
//        deptEquipInventory.put(equipmentId, deptEquipInventory.getOrDefault(equipmentId, 0) + 1);
//        department.setUpdatedAt(new Date());
//
//        return departmentRepository.save(department);
//    }
//
//    @Override
//    public List<Department> searchDepartments(String name, String location) {
//        if (name != null && location != null) {
//            // Simple example: filtering manually, you can create custom repo methods or use Mongo queries
//            List<Department> byName = departmentRepository.findByNameContainingIgnoreCase(name);
//            List<Department> byLocation = departmentRepository.findByLocationContainingIgnoreCase(location);
//
//            // intersection
//            Set<Department> byNameSet = new HashSet<>(byName);
//            byNameSet.retainAll(byLocation);
//            return new ArrayList<>(byNameSet);
//        } else if (name != null) {
//            return departmentRepository.findByNameContainingIgnoreCase(name);
//        } else if (location != null) {
//            return departmentRepository.findByLocationContainingIgnoreCase(location);
//        } else {
//            return departmentRepository.findAll();
//        }
//    }
//}




package com.mepms.service.impl;

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
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
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
            dep.setUpdatedAt(new Date());
            dep.setImage(department.getImage());
            if (department.getEquipmentInventory() != null) {
                Map<String, Integer> newInventory = department.getEquipmentInventory();
                Map<String, Integer> currentInventory = dep.getEquipmentInventory();
                if (currentInventory == null) {
                    dep.setEquipmentInventory(new HashMap<>(newInventory));
                } else {
                    // Merge by incrementing counts for existing equipment IDs
                    for (Map.Entry<String, Integer> entry : newInventory.entrySet()) {
                        String equipmentId = entry.getKey();
                        Integer newCount = entry.getValue();
                        Integer existingCount = currentInventory.getOrDefault(equipmentId, 0);
                        currentInventory.put(equipmentId, newCount);
                    }
                    dep.setEquipmentInventory(currentInventory);
                }
            }
            return departmentRepository.save(dep);
        }).orElseThrow(() -> new NoSuchElementException("Department not found"));
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department assignEquipmentToDepartment(String deptId, String equipmentId) throws Exception {
        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));

        Department inventoryDept = departmentRepository.findById(INVENTORY_DEPT_ID)
                .orElseThrow(() -> new NoSuchElementException("Inventory department not found"));

        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new NoSuchElementException("Equipment not found"));

        Integer currentInventoryCount = (inventoryDept.getEquipmentInventory() == null)
                ? 0
                : inventoryDept.getEquipmentInventory().getOrDefault(equipmentId, 0);

        if (currentInventoryCount <= 0) {
            throw new IllegalStateException("Equipment not available in inventory");
        }

        // Deduct 1 from inventory
        inventoryDept.getEquipmentInventory().put(equipmentId, currentInventoryCount - 1);

        // Update equipment stock
        equipment.setCurrentStock(Math.max(equipment.getCurrentStock() - 1, 0));
        equipmentRepository.save(equipment);

        departmentRepository.save(inventoryDept);

        // Update or add equipment count in target department
        Map<String, Integer> deptInventory = department.getEquipmentInventory();
        if (deptInventory == null) {
            deptInventory = new HashMap<>();
            department.setEquipmentInventory(deptInventory);
        }

        deptInventory.put(equipmentId, deptInventory.getOrDefault(equipmentId, 0) + 1);

        department.setUpdatedAt(new Date());

        return departmentRepository.save(department);
    }

    public Department transferEquipmentUnit(String equipmentId, String fromDeptId, String toDeptId) throws Exception {
        if (fromDeptId.equals(toDeptId)) {
            throw new IllegalArgumentException("Source and target departments must be different");
        }

        Department fromDept = departmentRepository.findById(fromDeptId)
                .orElseThrow(() -> new NoSuchElementException("Source department not found"));

        Department toDept = departmentRepository.findById(toDeptId)
                .orElseThrow(() -> new NoSuchElementException("Target department not found"));

        // Decrement equipment count in source department
        Map<String, Integer> fromInventory = fromDept.getEquipmentInventory();
        if (fromInventory == null || !fromInventory.containsKey(equipmentId)) {
            throw new IllegalStateException("Equipment not found in source department inventory");
        }
        int fromCount = fromInventory.get(equipmentId);
        if (fromCount <= 1) {
            fromInventory.remove(equipmentId);
        } else {
            fromInventory.put(equipmentId, fromCount - 1);
        }
        fromDept.setUpdatedAt(new Date());

        // Increment equipment count in target department
        Map<String, Integer> toInventory = toDept.getEquipmentInventory();
        if (toInventory == null) {
            toInventory = new HashMap<>();
            toDept.setEquipmentInventory(toInventory);
        }
        toInventory.put(equipmentId, toInventory.getOrDefault(equipmentId, 0) + 1);
        toDept.setUpdatedAt(new Date());

        departmentRepository.save(fromDept);
        departmentRepository.save(toDept);

        return toDept;
    }

    @Override
    public List<Department> searchDepartments(String name, String location) {
        if (name != null && location != null) {
            List<Department> byName = departmentRepository.findByNameContainingIgnoreCase(name);
            List<Department> byLocation = departmentRepository.findByLocationContainingIgnoreCase(location);

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
}

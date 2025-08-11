package com.mepms.service;



import java.util.List;
import java.util.Optional;

import com.mepms.entity.Department;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Optional<Department> getById(String id);
    Department createDepartment(Department department);
    Department updateDepartment(String id, Department department);
    void deleteDepartment(String id);

    /**
     * Assign equipment to department if stock available in inventory department.
     * Decrease inventory stock by 1 and increase in department.
     */
    Department assignEquipmentToDepartment(String deptId, String equipmentId) throws Exception;

    List<Department> searchDepartments(String name, String location);
    Department transferEquipmentUnit(String equipmentId, String fromDeptId, String toDeptId) throws Exception;
}

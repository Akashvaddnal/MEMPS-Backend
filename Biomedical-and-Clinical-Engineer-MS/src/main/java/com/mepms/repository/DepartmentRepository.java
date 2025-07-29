package com.mepms.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mepms.entity.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByNameContainingIgnoreCase(String name);
    List<Department> findByLocationContainingIgnoreCase(String location);
    Optional<Department> findByName(String name);
    
}

package com.mepms.controller;

import com.mepms.entity.RoleEO;
import com.mepms.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
	
	 @Autowired
    private RoleService roleService;
	 
    // Create a new Role
    @PostMapping
    public ResponseEntity<RoleEO> createRole(@Valid @RequestBody RoleEO role) {
        RoleEO createdRole = roleService.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    // Get all Roles
    @GetMapping
    public ResponseEntity<List<RoleEO>> getAllRoles() {
        List<RoleEO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Get Role by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleEO> getRoleById(@PathVariable String id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleEO> updateRole(@PathVariable String id, @Valid @RequestBody RoleEO role) {
        RoleEO updatedRole = roleService.updateRole(id, role);
        return ResponseEntity.ok(updatedRole);
    }

    // Delete Role and Related Users
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRoleId(id);
        return ResponseEntity.noContent().build();
    }
}

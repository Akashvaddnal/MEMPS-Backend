package com.mepms.controller;

import com.mepms.entity.UserEO;
import com.mepms.service.UserService;
import com.mepms.service.AuditLogService;
import com.mepms.entity.AuditLogEO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
    private UserService userService;

	@Autowired
    private AuditLogService auditLogService;

    // Create User
    @PostMapping
    public ResponseEntity<UserEO> createUser(@Valid @RequestBody UserEO user) {
        UserEO createdUser = userService.createUser(user);
        AuditLogEO log = new AuditLogEO();
        log.setUserId(createdUser.getId());
        log.setAction("CREATE_USER");
        log.setTimestamp(Instant.now());
        log.setDetails("User " + createdUser.getUsername() + " created by admin");
        auditLogService.saveAuditLog(log);
        return ResponseEntity.ok(createdUser);
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<List<UserEO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserEO> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserEO> updateUser(@PathVariable String id, @Valid @RequestBody UserEO user) {
        UserEO updatedUser = userService.updateUser(id, user);
        AuditLogEO log = new AuditLogEO();
        log.setUserId(updatedUser.getId());
        log.setAction("UPDATE_USER");
        log.setTimestamp(Instant.now());
        log.setDetails("User " + updatedUser.getUsername() + " updated by admin");
        auditLogService.saveAuditLog(log);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        AuditLogEO log = new AuditLogEO();
        log.setUserId(id);
        log.setAction("DELETE_USER");
        log.setTimestamp(Instant.now());
        log.setDetails("User with id " + id + " deleted by admin");
        auditLogService.saveAuditLog(log);
        return ResponseEntity.noContent().build();
    }
}
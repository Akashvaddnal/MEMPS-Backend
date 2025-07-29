package com.mepms.controllers;


import com.mepms.entity.UserEO;
import com.mepms.service.AuditLogService;
import com.mepms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final AuditLogService auditLogService;

    @Autowired
    public UserController(UserService userService, AuditLogService auditLogService) {
        this.userService = userService;
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<UserEO> createUser(@Valid @RequestBody UserEO user) {
        UserEO createdUser = userService.createUser(user);
        // Optionally add audit log here
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserEO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEO> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEO> updateUser(@PathVariable String id, @Valid @RequestBody UserEO user) {
        UserEO updatedUser = userService.updateUser(id, user);
        // Optionally add audit log here
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        // Optionally add audit log here
        return ResponseEntity.noContent().build();
    }
}

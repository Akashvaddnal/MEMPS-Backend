package com.mepms.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mepms.entity.AuditLogEO;
import com.mepms.entity.UserEO;
import com.mepms.service.AuditLogService;
import com.mepms.service.UserService;
import com.mepms.service.impl.FileStorageService;
import com.mepms.util.JwtUtil;

import jakarta.validation.Valid;

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
    @Autowired
	private FileStorageService fileStorageService;
    
    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserEO> createUserWithProfilePic(
            @RequestPart("user") @Valid UserEO user,
            @RequestPart("profilePic") MultipartFile profilePic) {

        if (!profilePic.isEmpty()) {
        	 // Assume this service is defined to handle file storage
            String fileName = fileStorageService.storeFile(profilePic); 
            // storeFile saves file and returns path or URL
            user.setProfilePic(fileName);
        }

        UserEO createdUser = userService.createUser(user);

        // ... audit log etc.
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
    
        @Autowired
    	private JwtUtil jwtUtil;
        
        @GetMapping("/current")
        public ResponseEntity<UserEO> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
            // Extract token by removing "Bearer " prefix
            final String token = authHeader.replace("Bearer ", "");

            // Use your JwtUtil to extract email or userId
            String email = jwtUtil.extractEmail(token);

            // Fetch user by email
            UserEO user = userService.getUserByEmail(email).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Optionally nullify or exclude sensitive fields (password)
            user.setPassword(null);

            return ResponseEntity.ok(user);
        }
    

}
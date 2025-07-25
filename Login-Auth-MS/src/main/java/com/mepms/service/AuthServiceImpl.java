package com.mepms.service;

import com.mepms.entity.User;
import com.mepms.repository.UserRepository;
import com.mepms.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${systemadminms.auditlog.url:http://localhost:8082/audit-logs}")
    private String auditLogUrl;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<?> register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Date now = new Date();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        userRepository.save(user);
        // Audit log for registration
        AuditLogRequest auditLog = new AuditLogRequest();
        auditLog.setUserId(user.getId());
        auditLog.setAction("REGISTER");
        auditLog.setTimestamp(java.time.Instant.now());
        auditLog.setDetails("User " + user.getUsername() + " registered");
        restTemplate.postForEntity(auditLogUrl, auditLog, Void.class);
        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public ResponseEntity<?> login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user);
        // Audit log
        AuditLogRequest auditLog = new AuditLogRequest();
        auditLog.setUserId(user.getId());
        auditLog.setAction("LOGIN");
        auditLog.setTimestamp(java.time.Instant.now());
        auditLog.setDetails("User " + user.getUsername() + " logged in");
        restTemplate.postForEntity(auditLogUrl, auditLog, Void.class);
        return ResponseEntity.ok(token);
    }

    class AuditLogRequest {
        private String userId;
        private String action;
        private java.time.Instant timestamp;
        private String details;
        // getters and setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public java.time.Instant getTimestamp() { return timestamp; }
        public void setTimestamp(java.time.Instant timestamp) { this.timestamp = timestamp; }
        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }
    }
}
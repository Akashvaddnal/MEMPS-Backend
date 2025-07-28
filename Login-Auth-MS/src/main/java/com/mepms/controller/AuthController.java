package com.mepms.controller;

import com.mepms.entity.User;
import com.mepms.service.AuthService;
import com.mepms.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return authService.login(user.getEmail(), user.getPassword());
    }
    
    
    @Autowired
	private JwtUtil jwtUtil;
    
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        // Extract token by removing "Bearer " prefix
        final String token = authHeader.replace("Bearer ", "");

        // Use your JwtUtil to extract email or userId
        String email = jwtUtil.extractEmail(token);

        // Fetch user by email
        User user = authService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Optionally nullify or exclude sensitive fields (password)
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

}

package com.mepms.service;

import com.mepms.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(User user);
    ResponseEntity<String> login(String email, String password);
    User findByEmail(String email);
}

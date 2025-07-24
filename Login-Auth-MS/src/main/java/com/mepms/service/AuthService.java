package com.mepms.service;

import com.mepms.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> register(User user);
    ResponseEntity<?> login(String email, String password);
}

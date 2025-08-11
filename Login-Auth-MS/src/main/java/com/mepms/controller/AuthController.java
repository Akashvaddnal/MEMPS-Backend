package com.mepms.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mepms.entity.User;
import com.mepms.repository.UserRepository;
import com.mepms.service.AuthService;
import com.mepms.service.EmailService;
import com.mepms.util.JwtUtil;
import com.mepms.util.OtpUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

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
    
    
    @PostMapping("/forgot-password-initiate")
    public ResponseEntity<?> forgotPasswordInitiate(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        User user = userRepo.findByEmail(email);
        if (user == null) return ResponseEntity.status(404).body("Email not registered.");

        String otp = OtpUtil.generateOtp(6);
        user.setResetPasswordOtp(otp);
        user.setResetPasswordOtpExpiry(new Date(System.currentTimeMillis() + 15*60*1000)); // 15 minutes
        userRepo.save(user);

        emailService.sendOtp(email, otp);
        return ResponseEntity.ok("OTP sent to your email");
    }

    // 2. Verify OTP
    @PostMapping("/forgot-password-verify")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String otp = req.get("otp");
        User user = userRepo.findByEmail(email);
        if (user == null) return ResponseEntity.status(404).body("Invalid request");
        if (user.getResetPasswordOtp() == null ||
            user.getResetPasswordOtpExpiry() == null ||
            !user.getResetPasswordOtp().equals(otp) ||
            user.getResetPasswordOtpExpiry().before(new Date())) {
            return ResponseEntity.status(400).body("Invalid or expired OTP");
        }
        return ResponseEntity.ok("OTP valid");
    }

    // 3. Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String newPassword = req.get("newPassword");
        String otp = req.get("otp");

        User user = userRepo.findByEmail(email);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        if (user.getResetPasswordOtp() == null ||
            user.getResetPasswordOtpExpiry() == null ||
            !user.getResetPasswordOtp().equals(otp) ||
            user.getResetPasswordOtpExpiry().before(new Date())) {
            return ResponseEntity.status(400).body("Invalid or expired OTP");
        }

        // Set new password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));
        // Clear otp fields
        user.setResetPasswordOtp(null);
        user.setResetPasswordOtpExpiry(null);
        userRepo.save(user);

        return ResponseEntity.ok("Password reset successful");
    }

}

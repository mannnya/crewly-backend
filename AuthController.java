package com.crewly.crewly.controller;

import com.crewly.crewly.entity.Student;
import com.crewly.crewly.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> body) {
        String result = authService.register(
                (String) body.get("fullName"),
                (String) body.get("email"),
                (String) body.get("password"),
                (String) body.get("department"),
                (Integer) body.get("year")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> body) {
        String result = authService.verifyOtp(
                body.get("email"),
                body.get("otp")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        Student student = authService.login(
                body.get("email"),
                body.get("password")
        );
        return ResponseEntity.ok(Map.of(
                "message", "Login successful!",
                "studentId", student.getStudentId(),
                "fullName", student.getFullName(),
                "email", student.getEmail()
        ));
    }

    // ── NEW: Forgot Password ──
    // Sends a reset OTP to the given email (reuses your existing OTP mechanism)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String result = authService.forgotPassword(body.get("email"));
        return ResponseEntity.ok(Map.of("message", result));
    }

    // ── NEW: Reset Password ──
    // Verifies the reset OTP and updates the password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String result = authService.resetPassword(
                body.get("email"),
                body.get("otp"),
                body.get("newPassword")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }
}

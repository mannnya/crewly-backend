package com.crewly.crewly.controller;

import com.crewly.crewly.entity.Student;
import com.crewly.crewly.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// ══════════════════════════════════════════════════════════════════════════════
// This is your FULL StudentController.
// Replace your existing StudentController.java with this file entirely.
// ══════════════════════════════════════════════════════════════════════════════

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    // Get all students (for the People tab)
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Get single student profile
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    // Update bio / department / year
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Integer studentId,
            @RequestBody Student body) {

        studentService.updateStudent(studentId, body);
        return ResponseEntity.ok("Updated successfully");
    }

    // ── NEW: Delete account ──
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok(Map.of("message", "Account deleted."));
    }

    // Get follower/connection count
    @GetMapping("/{studentId}/followers/count")
    public ResponseEntity<?> followerCount(@PathVariable Integer studentId) {
        long count = studentService.getFollowerCount(studentId);
        return ResponseEntity.ok(Map.of("count", count));
    }
    // Add this endpoint to StudentController.java
    @GetMapping("/{studentId}/following")
    public ResponseEntity<?> getFollowing(@PathVariable Integer studentId) {
        return ResponseEntity.ok(studentService.getFollowing(studentId));
    }

    // ── NEW: Follow / Connect with a student ──
    @PostMapping("/{studentId}/follow")
    public ResponseEntity<?> followStudent(@PathVariable Integer studentId,
                                           @RequestBody Map<String, Object> body) {
        Integer followerId = Integer.valueOf(body.get("followerId").toString());
        studentService.followStudent(followerId, studentId);
        return ResponseEntity.ok(Map.of("message", "Connected!"));
    }
}
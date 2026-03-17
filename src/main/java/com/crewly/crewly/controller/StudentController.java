package com.crewly.crewly.controller;

import com.crewly.crewly.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getProfile(@PathVariable Integer studentId) {
        return ResponseEntity.ok(studentService.getProfile(studentId));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer studentId,
                                           @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(studentService.updateProfile(
                studentId,
                (String) body.get("bio"),
                (String) body.get("department"),
                (Integer) body.get("year")
        ));
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@RequestBody Map<String, Integer> body) {
        String result = studentService.follow(
                body.get("followerId"),
                body.get("followingId")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestBody Map<String, Integer> body) {
        String result = studentService.unfollow(
                body.get("followerId"),
                body.get("followingId")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @GetMapping("/{studentId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable Integer studentId) {
        return ResponseEntity.ok(Map.of("count",
                studentService.getFollowersCount(studentId)));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByDepartment(
            @RequestParam String department) {
        return ResponseEntity.ok(
                studentService.searchByDepartment(department));
    }
}
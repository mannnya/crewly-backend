package com.crewly.crewly.controller;

import com.crewly.crewly.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(groupService.createGroup(
                (String) body.get("name"),
                (String) body.get("description"),
                (String) body.get("category"),
                (Integer) body.get("creatorId")
        ));
    }

    @GetMapping
    public ResponseEntity<?> getAllPublicGroups() {
        return ResponseEntity.ok(groupService.getAllPublicGroups());
    }

    @GetMapping("/my/{studentId}")
    public ResponseEntity<?> getMyGroups(@PathVariable Integer studentId) {
        return ResponseEntity.ok(groupService.getMyGroups(studentId));
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinGroup(@RequestBody Map<String, Integer> body) {
        String result = groupService.joinGroup(
                body.get("groupId"),
                body.get("studentId")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @DeleteMapping("/leave")
    public ResponseEntity<?> leaveGroup(@RequestBody Map<String, Integer> body) {
        String result = groupService.leaveGroup(
                body.get("groupId"),
                body.get("studentId")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }
}
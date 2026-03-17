package com.crewly.crewly.controller;

import com.crewly.crewly.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/group/{groupId}")
    public ResponseEntity<?> sendMessage(@PathVariable Integer groupId,
                                         @RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(messageService.sendGroupMessage(
                groupId,
                (Integer) body.get("senderId"),
                (String) body.get("content")
        ));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroupMessages(@PathVariable Integer groupId) {
        return ResponseEntity.ok(messageService.getGroupMessages(groupId));
    }

    @PostMapping("/group/{groupId}/pin")
    public ResponseEntity<?> pinMessage(@PathVariable Integer groupId,
                                        @RequestBody Map<String, Integer> body) {
        String result = messageService.pinMessage(
                groupId,
                body.get("messageId"),
                body.get("pinnedById")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }

    @GetMapping("/group/{groupId}/pinned")
    public ResponseEntity<?> getPinnedMessages(@PathVariable Integer groupId) {
        return ResponseEntity.ok(
                messageService.getPinnedMessages(groupId));
    }

    @DeleteMapping("/group/{groupId}/clear")
    public ResponseEntity<?> clearChat(@PathVariable Integer groupId,
                                       @RequestBody Map<String, Integer> body) {
        String result = messageService.clearChat(
                groupId,
                body.get("requesterId")
        );
        return ResponseEntity.ok(Map.of("message", result));
    }
}
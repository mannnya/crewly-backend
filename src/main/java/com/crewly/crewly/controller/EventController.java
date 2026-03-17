package com.crewly.crewly.controller;

import com.crewly.crewly.entity.EventParticipant;
import com.crewly.crewly.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Map<String, Object> body) {
        return ResponseEntity.ok(eventService.createEvent(
                (Integer) body.get("groupId"),
                (Integer) body.get("organizerId"),
                (String) body.get("title"),
                (String) body.get("description"),
                LocalDate.parse((String) body.get("eventDate")),
                LocalTime.parse((String) body.get("eventTime")),
                (String) body.get("location"),
                (Integer) body.get("maxCapacity")
        ));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingEvents() {
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getGroupEvents(@PathVariable Integer groupId) {
        return ResponseEntity.ok(eventService.getGroupEvents(groupId));
    }

    @PostMapping("/rsvp")
    public ResponseEntity<?> rsvp(@RequestBody Map<String, Object> body) {
        String result = eventService.rsvp(
                (Integer) body.get("eventId"),
                (Integer) body.get("studentId"),
                EventParticipant.RsvpStatus.valueOf((String) body.get("status"))
        );
        return ResponseEntity.ok(Map.of("message", result));
    }
}
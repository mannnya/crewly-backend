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

        // organizerId can arrive either as a flat integer OR as a nested { studentId: X } object
        Integer organizerId = null;
        Object organizerRaw = body.get("organizer");
        if (organizerRaw instanceof Map) {
            // frontend sends: organizer: { studentId: 5 }
            Object sid = ((Map<?, ?>) organizerRaw).get("studentId");
            if (sid != null) organizerId = ((Number) sid).intValue();
        }
        if (organizerId == null && body.get("organizerId") != null) {
            // fallback: flat organizerId field
            organizerId = ((Number) body.get("organizerId")).intValue();
        }

        // groupId is now optional (no group field in create event form)
        Integer groupId = body.get("groupId") != null
                ? ((Number) body.get("groupId")).intValue()
                : null;

        Integer maxCapacity = body.get("maxCapacity") != null
                ? ((Number) body.get("maxCapacity")).intValue()
                : null;

        return ResponseEntity.ok(eventService.createEvent(
                groupId,
                organizerId,
                (String) body.get("title"),
                (String) body.get("description"),
                LocalDate.parse((String) body.get("eventDate")),
                LocalTime.parse((String) body.get("eventTime")),
                (String) body.get("location"),
                maxCapacity
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
                ((Number) body.get("eventId")).intValue(),
                ((Number) body.get("studentId")).intValue(),
                EventParticipant.RsvpStatus.valueOf((String) body.get("status"))
        );
        return ResponseEntity.ok(Map.of("message", result));
    }
}
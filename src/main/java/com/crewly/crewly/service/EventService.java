package com.crewly.crewly.service;

import com.crewly.crewly.entity.*;
import com.crewly.crewly.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public Event createEvent(Integer groupId, Integer organizerId,
                             String title, String description,
                             LocalDate eventDate, LocalTime eventTime,
                             String location, Integer maxCapacity) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found!"));
        Student organizer = studentRepository.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        Event event = new Event();
        event.setGroup(group);
        event.setOrganizer(organizer);
        event.setTitle(title);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setEventTime(eventTime);
        event.setLocation(location);
        event.setMaxCapacity(maxCapacity);

        return eventRepository.save(event);
    }

    public String rsvp(Integer eventId, Integer studentId,
                       EventParticipant.RsvpStatus status) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found!"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        EventParticipantId id = new EventParticipantId();
        id.setEventId(eventId);
        id.setStudentId(studentId);

        EventParticipant participant = eventParticipantRepository
                .findById(id).orElse(new EventParticipant());
        participant.setId(id);
        participant.setEvent(event);
        participant.setStudent(student);
        participant.setRsvpStatus(status);

        eventParticipantRepository.save(participant);
        return "RSVP updated successfully!";
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqual(LocalDate.now());
    }

    public List<Event> getGroupEvents(Integer groupId) {
        return eventRepository.findByGroupGroupId(groupId);
    }
}
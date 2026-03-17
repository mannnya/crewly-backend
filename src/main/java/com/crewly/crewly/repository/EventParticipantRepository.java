package com.crewly.crewly.repository;

import com.crewly.crewly.entity.EventParticipant;
import com.crewly.crewly.entity.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
    List<EventParticipant> findByStudentStudentId(Integer studentId);
    List<EventParticipant> findByEventEventId(Integer eventId);
}
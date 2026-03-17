package com.crewly.crewly.repository;

import com.crewly.crewly.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByGroupGroupId(Integer groupId);
    List<Event> findByEventDateGreaterThanEqual(LocalDate date);
}
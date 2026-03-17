package com.crewly.crewly.repository;

import com.crewly.crewly.entity.PinnedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PinnedMessageRepository extends JpaRepository<PinnedMessage, Integer> {
    List<PinnedMessage> findByGroupGroupId(Integer groupId);
}
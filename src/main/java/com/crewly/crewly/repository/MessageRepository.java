package com.crewly.crewly.repository;

import com.crewly.crewly.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByReceiverGroupGroupIdOrderBySentAtAsc(Integer groupId);
    List<Message> findBySenderStudentIdAndReceiverStudentStudentId(Integer senderId, Integer receiverId);
}
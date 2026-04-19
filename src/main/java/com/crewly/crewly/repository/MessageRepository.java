package com.crewly.crewly.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.crewly.crewly.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE (m.sender.studentId = :a AND m.receiverStudent.studentId = :b) OR (m.sender.studentId = :b AND m.receiverStudent.studentId = :a) ORDER BY m.sentAt ASC")
    List<Message> findDMsBetween(@Param("a") Integer a, @Param("b") Integer b);
    List<Message> findByReceiverGroupGroupIdOrderBySentAtAsc(Integer groupId);
    List<Message> findBySenderStudentIdAndReceiverStudentStudentId(Integer senderId, Integer receiverId);
}
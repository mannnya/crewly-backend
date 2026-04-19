package com.crewly.crewly.repository;

import com.crewly.crewly.entity.Connection;
import com.crewly.crewly.entity.ConnectionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, ConnectionId> {
    List<Connection> findByFollowerStudentId(Integer followerId);
    List<Connection> findByFollowingStudentId(Integer followingId);
    Long countByFollowingStudentId(Integer followingId);
    Boolean existsByFollowerStudentIdAndFollowingStudentId(Integer followerId, Integer followingId);
}
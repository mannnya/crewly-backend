package com.crewly.crewly.repository;

import com.crewly.crewly.entity.GroupMember;
import com.crewly.crewly.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findByStudentStudentId(Integer studentId);
    List<GroupMember> findByGroupGroupId(Integer groupId);
    Boolean existsByGroupGroupIdAndStudentStudentId(Integer groupId, Integer studentId);
}
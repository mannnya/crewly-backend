package com.crewly.crewly.service;

import com.crewly.crewly.entity.Group;
import com.crewly.crewly.entity.GroupMember;
import com.crewly.crewly.entity.GroupMemberId;
import com.crewly.crewly.entity.Student;
import com.crewly.crewly.repository.GroupMemberRepository;
import com.crewly.crewly.repository.GroupRepository;
import com.crewly.crewly.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final StudentRepository studentRepository;

    public Group createGroup(String name, String description,
                             String category, Integer creatorId) {
        Student creator = studentRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        group.setCategory(category);
        group.setCreator(creator);

        Group saved = groupRepository.save(group);

        GroupMember member = new GroupMember();
        GroupMemberId memberId = new GroupMemberId();
        memberId.setGroupId(saved.getGroupId());
        memberId.setStudentId(creatorId);
        member.setId(memberId);
        member.setGroup(saved);
        member.setStudent(creator);
        member.setRole(GroupMember.Role.admin);
        groupMemberRepository.save(member);

        return saved;
    }

    public String joinGroup(Integer groupId, Integer studentId) {
        if (groupMemberRepository.existsByGroupGroupIdAndStudentStudentId(
                groupId, studentId)) {
            throw new RuntimeException("Already a member!");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found!"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        GroupMember member = new GroupMember();
        GroupMemberId memberId = new GroupMemberId();
        memberId.setGroupId(groupId);
        memberId.setStudentId(studentId);
        member.setId(memberId);
        member.setGroup(group);
        member.setStudent(student);
        member.setRole(GroupMember.Role.member);
        groupMemberRepository.save(member);

        return "Successfully joined the group!";
    }

    public String leaveGroup(Integer groupId, Integer studentId) {
        GroupMemberId id = new GroupMemberId();
        id.setGroupId(groupId);
        id.setStudentId(studentId);
        groupMemberRepository.deleteById(id);
        return "Successfully left the group!";
    }

    public List<Group> getAllPublicGroups() {
        return groupRepository.findByIsPublicTrue();
    }

    public List<GroupMember> getMyGroups(Integer studentId) {
        return groupMemberRepository.findByStudentStudentId(studentId);
    }
}
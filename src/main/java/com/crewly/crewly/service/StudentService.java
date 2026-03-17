package com.crewly.crewly.service;

import com.crewly.crewly.entity.Connection;
import com.crewly.crewly.entity.ConnectionId;
import com.crewly.crewly.entity.Student;
import com.crewly.crewly.repository.ConnectionRepository;
import com.crewly.crewly.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ConnectionRepository connectionRepository;

    public Student getProfile(Integer studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
    }

    public Student updateProfile(Integer studentId, String bio,
                                 String department, Integer year) {
        Student student = getProfile(studentId);
        student.setBio(bio);
        student.setDepartment(department);
        student.setYear(year);
        return studentRepository.save(student);
    }

    public String follow(Integer followerId, Integer followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("You cannot follow yourself!");
        }
        if (connectionRepository.existsByFollowerStudentIdAndFollowingStudentId(
                followerId, followingId)) {
            throw new RuntimeException("Already following!");
        }

        Student follower = studentRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
        Student following = studentRepository.findById(followingId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        Connection connection = new Connection();
        ConnectionId id = new ConnectionId();
        id.setFollowerId(followerId);
        id.setFollowingId(followingId);
        connection.setId(id);
        connection.setFollower(follower);
        connection.setFollowing(following);

        connectionRepository.save(connection);
        return "Successfully followed!";
    }

    public String unfollow(Integer followerId, Integer followingId) {
        ConnectionId id = new ConnectionId();
        id.setFollowerId(followerId);
        id.setFollowingId(followingId);
        connectionRepository.deleteById(id);
        return "Successfully unfollowed!";
    }

    public Long getFollowersCount(Integer studentId) {
        return connectionRepository.countByFollowingStudentId(studentId);
    }

    public List<Student> searchByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }
}
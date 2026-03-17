package com.crewly.crewly.dto;

import lombok.Data;

@Data
public class StudentDto {
    private Integer studentId;
    private String fullName;
    private String email;
    private String department;
    private Integer year;
    private String bio;
    private String profilePicUrl;
    private Boolean isActive;
    private Boolean isVerified;
    private Long followersCount;
}
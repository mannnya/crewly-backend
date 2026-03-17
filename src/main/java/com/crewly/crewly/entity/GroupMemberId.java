package com.crewly.crewly.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class GroupMemberId implements Serializable {
    private Integer groupId;
    private Integer studentId;
}
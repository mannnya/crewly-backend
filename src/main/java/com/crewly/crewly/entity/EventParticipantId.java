package com.crewly.crewly.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class EventParticipantId implements Serializable {
    private Integer eventId;
    private Integer studentId;
}
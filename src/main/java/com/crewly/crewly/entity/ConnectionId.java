package com.crewly.crewly.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ConnectionId implements Serializable {
    private Integer followerId;
    private Integer followingId;
}
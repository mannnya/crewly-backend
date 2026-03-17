package com.crewly.crewly.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "connections")
public class Connection {

    @EmbeddedId
    private ConnectionId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private Student follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private Student following;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
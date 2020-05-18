package com.meetings.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Entity
@Table(name = "meetings")
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Meeting {

  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  UUID id;

  @Column(nullable = false)
  Date fromAt;

  @Column(nullable = false)
  Date toAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  Date updatedAt;

  @Column(nullable = false)
  Date createdAt = new Date();
}

package com.meetings.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
@Builder(toBuilder = true)
@Entity
@Table(
    name = "privilege",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Privilege implements GrantedAuthority, Serializable {
  public static final String READ = "READ";
  public static final String WRITE = "WRITE";

  public static final String READ_USER = "READ_USER";
  public static final String WRITE_USER = "WRITE_USER";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  Long id;

  @Column(nullable = false)
  String name;

  @Column(nullable = false)
  Date createdAt = new Date();

  @Override
  public String getAuthority() {
    return name;
  }
}

package com.mittings.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@Entity
@Table(
    name = "role",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Role implements Serializable {
  public static final String ADMIN = "ADMIN";
  public static final String USER = "USER";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false, updatable = false)
  Long id;

  @Column(nullable = false, unique = true)
  String name;

  @Column(nullable = false)
  Date createdAt = new Date();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "roles_privileges",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
  Collection<Privilege> privileges;
}

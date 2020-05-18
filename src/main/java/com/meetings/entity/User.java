package com.meetings.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails {
  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  UUID id;

  String username;

  @Column(nullable = false)
  @NonNull
  String password;

  @Column(nullable = false)
  boolean enabled;

  @Column(nullable = false)
  boolean accountNonExpired;

  @Column(nullable = false)
  boolean accountNonLocked;

  @Column(nullable = false)
  boolean credentialsNonExpired;

  Date updatedAt;

  @Column(nullable = false)
  Date createdAt = new Date();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  Collection<Role> roles;

  @OneToMany(mappedBy = "user")
  Collection<Meeting> meetings;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Objects.requireNonNull(roles)
        .stream()
        .flatMap(role -> role.getPrivileges().stream())
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }
}

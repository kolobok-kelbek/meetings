package com.mittings.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
public final class User implements UserDetails {
  @Id
  @GeneratedValue
  @Column(nullable = false, updatable = false)
  private UUID id;

  private String username;

  @Column(nullable = false)
  @NonNull
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private boolean accountNonExpired;

  @Column(nullable = false)
  private boolean accountNonLocked;

  @Column(nullable = false)
  private boolean credentialsNonExpired;

  private Date updatedAt;

  @Column(nullable = false)
  private Date createdAt = new Date();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

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

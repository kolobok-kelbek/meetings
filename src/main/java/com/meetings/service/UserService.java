package com.meetings.service;

import com.meetings.entity.Privilege;
import com.meetings.entity.Role;
import com.meetings.entity.User;
import com.meetings.repository.RoleRepository;
import com.meetings.repository.UserRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsManager {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
  }

  public User getCurrentUser() {
    String username =
        (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return findUserByUsername(username);
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public User findUserById(UUID id) {
    return userRepository.findById(id).orElse(null);
  }

  public Role findRole(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    User user = userRepository.findByUsername(username).orElse(null);

    if (null == user) {
      return new org.springframework.security.core.userdetails.User(
          "",
          "",
          true,
          true,
          true,
          true,
          getAuthorities(Collections.singletonList(roleRepository.findByName(Role.USER))));
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        getAuthorities(user.getRoles()));
  }

  public void createUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void createUser(UserDetails user) {
    createUser((User) user);
  }

  public void updateUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void updateUser(UserDetails user) {
    updateUser((User) user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  @Override
  public void deleteUser(String username) {
    deleteUser(userRepository.findByUsername(username).orElseThrow());
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {}

  @Override
  public boolean userExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    return getGrantedAuthorities(getPrivileges(roles));
  }

  private List<String> getPrivileges(Collection<Role> roles) {

    List<String> privileges = new ArrayList<>();
    List<Privilege> collection = new ArrayList<>();

    for (Role role : roles) {
      collection.addAll(role.getPrivileges());
    }

    for (Privilege item : collection) {
      privileges.add(item.getName());
    }

    return privileges;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (String privilege : privileges) {
      authorities.add(new SimpleGrantedAuthority(privilege));
    }

    return authorities;
  }
}

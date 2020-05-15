package com.meetings.converter.user;

import com.meetings.entity.Role;
import com.meetings.entity.User;
import com.meetings.model.input.Sign;
import com.meetings.service.UserService;
import java.util.Collections;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SignToUserConverter implements Converter<Sign, User> {
  private UserService userService;

  @Autowired
  public SignToUserConverter(UserService userService) {
    this.userService = userService;
  }

  @Override
  public User convert(MappingContext<Sign, User> context) {
    Sign sign = context.getSource();

    return User.builder()
        .username(sign.getUsername())
        .password(passwordEncoder().encode(sign.getPassword()))
        .enabled(true)
        .roles(Collections.singletonList(userService.findRole(Role.USER)))
        .build();
  }

  private PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}

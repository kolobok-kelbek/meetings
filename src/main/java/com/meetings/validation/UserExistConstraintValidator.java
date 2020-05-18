package com.meetings.validation;

import com.meetings.service.UserService;
import com.meetings.validation.constraints.UserExist;
import java.util.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserExistConstraintValidator implements ConstraintValidator<UserExist, String> {
  private final UserService userService;

  @Autowired
  public UserExistConstraintValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void initialize(UserExist userExist) {}

  @Override
  public boolean isValid(String userId, ConstraintValidatorContext ctx) {
    return null != userService.findUserById(UUID.fromString(userId));
  }
}

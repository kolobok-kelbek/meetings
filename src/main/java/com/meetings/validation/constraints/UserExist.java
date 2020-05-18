package com.meetings.validation.constraints;

import com.meetings.validation.UserExistConstraintValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UserExistConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExist {
  String message() default "{com.meetings.validation.user.exist}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

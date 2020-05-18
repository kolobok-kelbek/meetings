package com.meetings.validation.constraints;

import com.meetings.validation.FreeDataBetweenConstraintValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = FreeDataBetweenConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeDataBetween {
  String message() default "{com.meetings.validation.free_data_between}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

package com.meetings.model.input;

import com.meetings.validation.constraints.FreeDataBetween;
import com.meetings.validation.constraints.UserExist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@FreeDataBetween
public class Meeting {
  @NotEmpty(message = "Please provide a user id.")
  @UserExist(message = "User with this id not found.")
  @Pattern(
      regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$",
      message = "Please provide a correct user id (uuid v4).")
  String userId;

  @NotEmpty(message = "Please provide a from date.")
  @Pattern(
      regexp = "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$",
      message = "Please provide a correct from date (format - \"yyyy-MM-dd'T'HH:mm:ss\").")
  String from;

  @NotEmpty(message = "Please provide a to date.")
  @Pattern(
      regexp = "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$",
      message = "Please provide a correct to date (format - \"yyyy-MM-dd'T'HH:mm:ss\").")
  String to;
}

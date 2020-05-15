package com.meetings.model.input;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class Sign {

  @NotEmpty(message = "Please provide a username number.")
  @Size(
      min = 4,
      max = 255,
      message = "Please enter a username of at least 4 and at most 255 characters.")
  private String username;

  @NotEmpty(message = "Please provide a password.")
  @Size(
      min = 8,
      max = 48,
      message = "Please enter a password of at least 8 and at most 48 characters.")
  private String password;
}

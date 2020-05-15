package com.meetings.model.view;

import java.util.UUID;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserView {

  private final UUID id;

  private final String username;
}

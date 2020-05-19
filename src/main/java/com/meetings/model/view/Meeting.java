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
public class Meeting {
  UUID id;

  UUID userId;

  String from;

  String to;
}

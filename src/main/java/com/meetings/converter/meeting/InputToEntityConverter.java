package com.meetings.converter.meeting;

import com.meetings.model.input.Meeting;
import com.meetings.service.UserService;
import java.text.SimpleDateFormat;
import java.util.UUID;
import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputToEntityConverter implements Converter<Meeting, com.meetings.entity.Meeting> {
  private final UserService userService;

  @Autowired
  public InputToEntityConverter(UserService userService) {
    this.userService = userService;
  }

  @SneakyThrows
  @Override
  public com.meetings.entity.Meeting convert(
      MappingContext<Meeting, com.meetings.entity.Meeting> context) {
    Meeting meeting = context.getSource();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return com.meetings.entity.Meeting.builder()
        .user(userService.findUserById(UUID.fromString(meeting.getUserId())))
        .fromAt(dateFormat.parse(meeting.getFrom()))
        .toAt(dateFormat.parse(meeting.getTo()))
        .build();
  }
}

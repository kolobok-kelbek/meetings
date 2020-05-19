package com.meetings.converter.meeting;

import com.meetings.entity.Meeting;
import java.text.SimpleDateFormat;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class EntityToViewConverter implements Converter<Meeting, com.meetings.model.view.Meeting> {
  @Override
  public com.meetings.model.view.Meeting convert(
      MappingContext<Meeting, com.meetings.model.view.Meeting> context) {
    Meeting meeting = context.getSource();

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return com.meetings.model.view.Meeting.builder()
        .id(meeting.getId())
        .userId(meeting.getUser().getId())
        .from(format.format(meeting.getFromAt()))
        .to(format.format(meeting.getToAt()))
        .build();
  }
}

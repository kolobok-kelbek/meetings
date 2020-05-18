package com.meetings.validation;

import com.meetings.model.input.Meeting;
import com.meetings.repository.MeetingRepository;
import com.meetings.validation.constraints.FreeDataBetween;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class FreeDataBetweenConstraintValidator
    implements ConstraintValidator<FreeDataBetween, Meeting> {
  private final MeetingRepository meetingRepository;

  private final int MIN_MINUTES = 30;
  private final int MAX_HOURS = 24;

  @Autowired
  public FreeDataBetweenConstraintValidator(MeetingRepository meetingRepository) {
    this.meetingRepository = meetingRepository;
  }

  @Override
  public void initialize(FreeDataBetween freeDataBetween) {}

  @Override
  public boolean isValid(Meeting meeting, ConstraintValidatorContext ctx) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date from;
    Date to;

    try {
      from = dateFormat.parse(meeting.getFrom());
      to = dateFormat.parse(meeting.getTo());
    } catch (ParseException parseException) {
      return false;
    }

    long diff = to.getTime() - from.getTime();
    int diffMinutes = (int) (diff / (60 * 1000));

    if (diffMinutes < MIN_MINUTES) {
      return false;
    }

    int diffHours = (int) (diff / (60 * 60 * 1000));

    if (diffHours > MAX_HOURS) {
      return false;
    }

    return meetingRepository.countBetween(from, to) <= 0;
  }
}

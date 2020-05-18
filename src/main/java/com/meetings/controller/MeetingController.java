package com.meetings.controller;

import com.meetings.converter.Converter;
import com.meetings.converter.SimpleConverter;
import com.meetings.converter.exception.ConvertException;
import com.meetings.converter.factory.ConverterFactory;
import com.meetings.entity.Meeting;
import com.meetings.model.input.PaginationQuery;
import com.meetings.model.view.ListView;
import com.meetings.repository.MeetingRepository;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/meetings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeetingController {
  private final MeetingRepository meetingRepository;
  private final SimpleConverter<Meeting, com.meetings.model.view.Meeting> meetingViewConverter;
  private final SimpleConverter<com.meetings.model.input.Meeting, Meeting> meetingEntityConverter;
  private final Converter<
          Page<Meeting>, ListView<com.meetings.model.view.Meeting>, com.meetings.model.view.Meeting>
      listViewConverter;

  @Autowired
  public MeetingController(
      MeetingRepository meetingRepository, final ConverterFactory converterFactory) {
    this.meetingRepository = meetingRepository;
    this.meetingViewConverter = converterFactory.createSimpleConverter();
    this.meetingEntityConverter = converterFactory.createSimpleConverter();
    this.listViewConverter = converterFactory.createPageToListViewConverter();
  }

  @PostMapping
  public com.meetings.model.view.Meeting createMeeting(
      @Valid @RequestBody final com.meetings.model.input.Meeting inputMeeting)
      throws ConvertException {
    Meeting meeting = meetingEntityConverter.convert(inputMeeting, Meeting.class);
    Meeting meetingSaved = meetingRepository.save(meeting);

    return meetingViewConverter.convert(meetingSaved, com.meetings.model.view.Meeting.class);
  }

  @PutMapping("/{uuid}")
  public com.meetings.model.view.Meeting fullUpdateMeeting(
      @PathVariable final UUID uuid,
      @Valid @RequestBody final com.meetings.model.input.Meeting inputMeeting)
      throws ConvertException {
    Meeting meeting = meetingEntityConverter.convert(inputMeeting, Meeting.class);

    Meeting meetingIdentified =
        Meeting.builder()
            .id(uuid)
            .user(meeting.getUser())
            .fromAt(meeting.getFromAt())
            .toAt(meeting.getToAt())
            .build();

    Meeting meetingSaved = meetingRepository.save(meetingIdentified);

    return meetingViewConverter.convert(meetingSaved, com.meetings.model.view.Meeting.class);
  }

  @GetMapping
  public ListView<com.meetings.model.view.Meeting> getMeetingList(
      @Valid PaginationQuery paginationQuery) throws ConvertException {

    Page<Meeting> page =
        meetingRepository.findAll(
            PageRequest.of(paginationQuery.getOffset(), paginationQuery.getLimit()));

    return listViewConverter.convert(page, com.meetings.model.view.Meeting.class);
  }

  @GetMapping("/{uuid}")
  public com.meetings.model.view.Meeting getMeeting(@PathVariable final UUID uuid)
      throws ConvertException {

    Meeting meetingSaved =
        meetingRepository
            .findById(uuid)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"));

    return meetingViewConverter.convert(meetingSaved, com.meetings.model.view.Meeting.class);
  }
}

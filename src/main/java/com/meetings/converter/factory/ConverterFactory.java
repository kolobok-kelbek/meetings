package com.meetings.converter.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetings.converter.Converter;
import com.meetings.converter.ObjectConverter;
import com.meetings.converter.PageToListViewConverter;
import com.meetings.converter.SimpleConverter;
import com.meetings.converter.user.SignToUserConverter;
import com.meetings.model.view.ListView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ConverterFactory {
  private final ObjectMapper jsonMapper;

  private final ModelMapper modelMapper;

  @Autowired
  public ConverterFactory(
      final ObjectMapper jsonMapper,
      final ModelMapper modelMapper,
      final SignToUserConverter signToUserConverter) {
    this.jsonMapper = jsonMapper;
    this.modelMapper = modelMapper;

    this.modelMapper.addConverter(signToUserConverter);
  }

  public <IN, OUT> SimpleConverter<IN, OUT> createSimpleConverter() {
    return new ObjectConverter<>(jsonMapper, modelMapper);
  }

  public <IN, OUT> Converter<Page<IN>, ListView<OUT>, OUT> createPageToListViewConverter() {
    return new PageToListViewConverter<>(createSimpleConverter());
  }
}

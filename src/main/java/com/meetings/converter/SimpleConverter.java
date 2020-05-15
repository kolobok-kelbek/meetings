package com.meetings.converter;

import com.meetings.converter.exception.ConvertException;

@FunctionalInterface
public interface SimpleConverter<IN, OUT> extends Converter<IN, OUT, OUT> {
  public OUT convert(IN data, Class<OUT> outClass) throws ConvertException;
}

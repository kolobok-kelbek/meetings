package com.mittings.converter;

import com.mittings.converter.exception.ConvertException;

@FunctionalInterface
public interface SimpleConverter<IN, OUT> extends Converter<IN, OUT, OUT> {
  public OUT convert(IN data, Class<OUT> outClass) throws ConvertException;
}

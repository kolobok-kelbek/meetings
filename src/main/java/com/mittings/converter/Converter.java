package com.mittings.converter;

import com.mittings.converter.exception.ConvertException;

@FunctionalInterface
public interface Converter<IN, OUT, TYPE> {
  public OUT convert(IN data, Class<TYPE> outClass) throws ConvertException;
}

package com.meetings.model.input;

import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class PaginationQuery {
  int DEFAULT_LIMIT = 10;
  int DEFAULT_OFFSET = 0;

  @Min(value = 0, message = "Invalid value of limit.")
  Integer limit;

  @Min(value = 0, message = "Invalid value of offset.")
  Integer offset;

  public Integer getLimit() {
    return null == limit ? DEFAULT_LIMIT : limit;
  }

  public Integer getOffset() {
    return null == offset ? DEFAULT_OFFSET : offset;
  }
}

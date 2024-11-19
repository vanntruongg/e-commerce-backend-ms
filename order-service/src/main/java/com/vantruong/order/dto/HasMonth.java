package com.vantruong.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasMonth {
  String getMonth();
  @JsonIgnore
  Number getValue();
}

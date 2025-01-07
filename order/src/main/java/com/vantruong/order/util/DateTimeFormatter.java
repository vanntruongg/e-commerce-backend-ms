package com.vantruong.order.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DateTimeFormatter {
  private static final long SECONDS_PER_MINUTE = 60L;
  private static final long SECONDS_PER_HOUR = SECONDS_PER_MINUTE * 60;
  private static final long SECONDS_PER_DAY = SECONDS_PER_HOUR * 24;

  Map<Long, Function<LocalDateTime, String>> strategyMap = new LinkedHashMap<>();

  public DateTimeFormatter() {
    strategyMap.put(SECONDS_PER_MINUTE, this::formatInSeconds);
    strategyMap.put(SECONDS_PER_HOUR, this::formatInMinutes);
    strategyMap.put(SECONDS_PER_DAY, this::formatInHours);
    strategyMap.put(Long.MAX_VALUE, this::formatInDate);
  }


  private String formatInSeconds(LocalDateTime dateTime) {
    long elapseSeconds = ChronoUnit.SECONDS.between(dateTime, LocalDateTime.now());
    return elapseSeconds + " giây trước";
  }

  private String formatInMinutes(LocalDateTime dateTime) {
    long elapseMinutes = ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now());
    return elapseMinutes + " phút trước";
  }

  private String formatInHours(LocalDateTime dateTime) {
    long elapseHours = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
    return elapseHours + " giờ trước";
  }

  private String formatInDate(LocalDateTime dateTime) {
    LocalDateTime localDateTime = dateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
    java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    return localDateTime.format(dateTimeFormatter);
  }

  public String format(LocalDateTime dateTime) {
    long elapseSeconds = ChronoUnit.SECONDS.between(dateTime, LocalDateTime.now());

    var strategy = strategyMap.entrySet().stream()
            .filter(longFunctionEntry -> elapseSeconds < longFunctionEntry.getKey())
            .findFirst()
            .get();

    return strategy.getValue().apply(dateTime);
  }
}

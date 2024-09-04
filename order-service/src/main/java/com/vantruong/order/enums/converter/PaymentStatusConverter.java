package com.vantruong.order.enums.converter;

import com.vantruong.order.enums.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {
  @Override
  public String convertToDatabaseColumn(PaymentStatus status) {
    return status != null ? status.name() : null;
  }

  @Override
  public PaymentStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;

    return Stream.of(PaymentStatus.values())
            .filter(status -> status.name().equals(dbData))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
  }
}

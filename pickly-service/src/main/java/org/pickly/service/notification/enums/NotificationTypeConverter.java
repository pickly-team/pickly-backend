package org.pickly.service.notification.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(NotificationType attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getType();
  }

  @Override
  public NotificationType convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return NotificationType.find(dbData);
  }

}

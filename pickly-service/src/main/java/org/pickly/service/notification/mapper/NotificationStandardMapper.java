package org.pickly.service.notification.mapper;

import org.pickly.service.notification.controller.request.NotificationStandardCreateReq;
import org.pickly.service.notification.controller.request.NotifyStandardDayUpdateReq;
import org.pickly.service.notification.controller.response.NotificationStandardRes;
import org.pickly.service.notification.entity.NotificationStandard;
import org.pickly.service.notification.service.dto.NotificationStandardDTO;
import org.pickly.service.notification.service.dto.NotifyStandardDayUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class NotificationStandardMapper {

  public NotificationStandardDTO toCreateDTO(NotificationStandardCreateReq request) {
    return new NotificationStandardDTO(
        request.getIsActive(),
        request.getNotifyDailyAt()
    );
  }

  public NotificationStandardDTO toUpdateDTO(NotificationStandardCreateReq request) {
    return new NotificationStandardDTO(
        request.getIsActive(),
        request.getNotifyDailyAt()
    );
  }

  public NotifyStandardDayUpdateDTO toDto(NotifyStandardDayUpdateReq request) {
    return new NotifyStandardDayUpdateDTO(request.getNotifyStandardDay());
  }

  public NotificationStandardRes toResponse(NotificationStandard dto) {
    return new NotificationStandardRes(
        dto.getIsActive(),
        dto.getNotifyDailyAt()
    );
  }
}

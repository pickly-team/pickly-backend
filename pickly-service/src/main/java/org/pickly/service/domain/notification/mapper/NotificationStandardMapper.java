package org.pickly.service.domain.notification.mapper;

import org.pickly.service.domain.notification.dto.controller.request.NotificationStandardCreateReq;
import org.pickly.service.domain.notification.dto.controller.request.NotifyStandardDayUpdateReq;
import org.pickly.service.domain.notification.dto.controller.response.NotificationStandardRes;
import org.pickly.service.domain.notification.dto.service.NotificationStandardDTO;
import org.pickly.service.domain.notification.dto.service.NotifyStandardDayUpdateDTO;
import org.pickly.service.domain.notification.entity.NotificationStandard;
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

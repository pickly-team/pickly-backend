package org.pickly.service.notification.service.interfaces;

import org.pickly.service.notification.entity.NotificationStandard;

public interface NotificationStandardService {

  NotificationStandard findByMember(Long memberId);

}

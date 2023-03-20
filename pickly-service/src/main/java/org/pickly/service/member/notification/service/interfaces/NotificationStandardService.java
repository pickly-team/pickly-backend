package org.pickly.service.member.notification.service.interfaces;

import org.pickly.service.member.notification.entity.NotificationStandard;

public interface NotificationStandardService {

  NotificationStandard findByMember(Long memberId);

}

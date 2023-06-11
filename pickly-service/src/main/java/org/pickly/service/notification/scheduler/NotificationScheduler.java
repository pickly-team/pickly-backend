package org.pickly.service.notification.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationScheduler {

  private final NotificationService notificationService;

  @Scheduled(cron = "0 0,30 * * * *")
  public void checkBookmark() {

    // 아직 읽지 않은 북마크 전체 조회 -> Map<Member, List<Bookmark>> 형태로

    // entry 돌면서

    // Member의 알람 기준 일 조회

    // 북마크 생성일자 + member의 알람 기준일 <= 현재 시간인 경우 Map에 추가 -> Map<Bookmark, LocalDateTime>
    // LocalDateTime = bookmark 생성일자 + member 알람 기준일 + memeber 알람 선호 시간

    // 알림 DB에 저장, is_send = false

  }

  @Scheduled(cron = "0 0,30 * * * *")
  public void sendNotification() {

    // 알림 DB 조회, is_send = false 인 친구들만

    // 현재 시간 = Notification.sendTime 인 친구들만 List에 담기

    // FCM을 이용해 송신

  }

}

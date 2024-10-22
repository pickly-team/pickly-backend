package org.pickly.service.domain.notification.common;

import com.google.common.collect.Lists;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.domain.member.service.MemberReadService;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.service.NotificationWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationSender {

  private final MemberReadService memberReadService;
  private final NotificationWriteService notificationWriteService;

  // FCM 메세지 송신 메서드
  public void sendMessage(List<Notification> notifications) {
    // 메세지를 받을 유저를 파악합니다 (= 유저의 fcm token 값 조회)
    Map<Long, String> memberTokens = createMemberFcmTokenMap(notifications);

    // 유저에게 송신할 메세지를 생성합니다 (FCM 양식인 Message에 맞춰서 생성)
    List<Message> messages = createMessage(notifications, memberTokens);

    // 메세지 수가 많은 경우를 대비, 200개 단위로 파티션을 끊습니다.
    List<List<Message>> messagePartition = Lists.partition(messages, 200);

    // 200개 단위로 FCM 메세지를 송신합니다. 만약 송신에 실패할 경우 로그를 남깁니다.
    messagePartition
        .stream()
        .map(messageList -> {
          try {
            FirebaseMessaging.getInstance().sendAll(messageList);
            return true;

          } catch (final FirebaseMessagingException e) {
            log.error("fail to send message. error info = {}", e.getMessage());
            return false;
          }
        })
        .forEach(isSuccess -> {
          if (isSuccess) {
            log.info("success to send message");
          }
        });

    // 송신한 메세지의 is_send 값을 true로 업데이트해줍니다.
    notificationWriteService.updateAllToSend(notifications);
  }

  // 유저의 fcm token 조회를 진행한다. notification의 memberId의 개수가
  private Map<Long, String> createMemberFcmTokenMap(
      final List<Notification> notifications
  ) {

    // Set을 통한 중복 제거
    final Set<Long> memberIds = notifications.stream()
        .map(Notification::getMemberId)
        .collect(Collectors.toSet());

    return memberReadService.findTokenByIds(memberIds);
  }

  private List<Message> createMessage(
      final List<Notification> notifications,
      final Map<Long, String> memberTokens
  ) {
    List<Message> messages = new ArrayList<>();

    for (Notification notification : notifications) {
      String fcmToken = memberTokens.get(notification.getMemberId());
      try {
        messages.add(
            Message.builder()
                .putData("bookmarkId", String.valueOf(notification.getBookmarkId()))
                .setNotification(com.google.firebase.messaging.Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getContent())
                    .build())
                .setToken(fcmToken)
                .build()
        );
      } catch (IllegalArgumentException e) {
        log.error("fail to make message. error info = {}, memberId = {}, fcm token = {}",
            e.getMessage(), notification.getMemberId(), fcmToken);
      }
    }
    return messages;
  }


}

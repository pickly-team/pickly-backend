package org.pickly.service.notification.common;

import com.google.common.collect.Lists;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.member.service.interfaces.MemberService;
import org.pickly.service.notification.entity.Notification;
import org.pickly.service.notification.service.interfaces.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationSender {

  private final MemberService memberService;
  private final NotificationService notificationService;

  // FCM 메세지 송신 메서드
  public void sendMessage(List<Notification> notifications) {
    // 메세지를 받을 유저를 파악합니다 (= 유저의 fcm token 값 조회)
    Map<Long, String> memberTokens = createMemberFcmTokenMap(notifications);
    // 유저에게 송신할 메세지를 생성합니다 (FCM 양식인 Message에 맞춰서 생성)
    List<Message> messages = createMessage(notifications, memberTokens);

    // 메세지 수가 많은 경우를 대비, 200개 단위로 파티션을 끊습니다.
    List<List<Message>> messagePartition = Lists.partition(messages, 200);
    // 200개 단위로 FCM 메세지를 송신합니다. 만약 송신에 실패할 경우 로그를 남깁니다.
    for (List<Message> messageList : messagePartition) {
      try {
        FirebaseMessaging.getInstance().sendAll(messageList);
      } catch (FirebaseMessagingException e) {
        log.error("fail to send message. error info = {}", e.getMessage());
      }
    }

    // 송신한 메세지의 is_send 값을 true로 업데이트해줍니다.
    notificationService.updateAllToSend(notifications);
  }

  private Map<Long, String> createMemberFcmTokenMap(List<Notification> notifications) {
    List<Long> memberIds = notifications.stream().map(Notification::getMemberId).toList();
    return memberService.findTokenByIds(memberIds);
  }

  private List<Message> createMessage(
      List<Notification> notifications, Map<Long, String> memberTokens
  ) {
    List<Message> messages = new ArrayList<>();
    for (Notification notification : notifications) {
      try {
        messages.add(
            Message.builder()
                .putData("bookmarkId", String.valueOf(notification.getBookmarkId()))
                .setNotification(com.google.firebase.messaging.Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getContent())
                    .build())
                .setToken(memberTokens.get(notification.getMemberId()))
                .build()
        );
      } catch (IllegalArgumentException e) {
        log.error("fail to make message. error info = {}", e.getMessage());
      }
    }
    return messages;
  }


}

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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationSender {

  private final MemberService memberService;
  private final NotificationService notificationService;

  public void sendMessage(List<Notification> notifications) {
    Map<Long, String> memberTokens = createMemberFcmTokenMap(notifications);
    List<Message> messages = createMessage(notifications, memberTokens);

    // partition messages
    List<List<Message>> messagePartition = Lists.partition(messages, 200);
    for (List<Message> messageList : messagePartition) {
      try {
        FirebaseMessaging.getInstance().sendAll(messageList);
      } catch (FirebaseMessagingException e) {
        log.error("fail to send message. error info = {}", e.getMessage());
      }
    }

    // all messages update -> is_send = true
    notificationService.updateAllToSend(notifications);
  }

  private Map<Long, String> createMemberFcmTokenMap(List<Notification> notifications) {
    List<Long> memberIds = notifications.stream().map(Notification::getMemberId).toList();
    return memberService.findTokenByIds(memberIds);
  }

  private List<Message> createMessage(
      List<Notification> notifications, Map<Long, String> memberTokens
  ) {
    return notifications.stream().map(
            notification -> Message.builder()
                .putData("bookmarkId", String.valueOf(notification.getBookmarkId()))
                .setNotification(com.google.firebase.messaging.Notification.builder()
                    .setTitle(notification.getTitle())
                    .setBody(notification.getContent())
                    .build())
                .setToken(memberTokens.get(notification.getMemberId()))
                .build())
        .collect(Collectors.toList());
  }


}

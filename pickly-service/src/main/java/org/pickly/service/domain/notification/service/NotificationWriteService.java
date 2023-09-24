package org.pickly.service.domain.notification.service;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.notification.repository.interfaces.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationWriteService {

  private final NotificationRepository notificationRepository;

  public void deleteByBookmark(Long bookmarkId) {
    notificationRepository.deleteAllByBookmarkId(bookmarkId);
  }

  public void deleteByBookmark(List<Long> bookmarkIds) {
    notificationRepository.deleteAllByBookmarkIdIn(bookmarkIds);
  }

  public void deleteByMember(Long memberId) {
    notificationRepository.deleteAllByMemberId(memberId);
  }

}

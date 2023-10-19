package org.pickly.service.domain.notification.repository.interfaces;

import org.pickly.service.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  @Query("select n from Notification n " +
      "where n.isSend = true and n.memberId = :memberId and n.deletedAt is null " +
      "order by n.createdAt desc")
  List<Notification> findAllByMemberIdAndDeletedAtNull(@Param("memberId") Long memberId);

  @Query("select n from Notification n where n.isSend = false and n.sendDateTime <= :now" +
      " and n.deletedAt is null")
  List<Notification> getNotificationsToSend(@Param("now") LocalDateTime now);

  @Query("select n from Notification n "
      + "where n.isSend = true and n.deletedAt is null and n.id = :id")
  Optional<Notification> findByIdAndDeletedAtNull(@Param("id") Long id);

  @Modifying
  @Query("update Notification n set n.isChecked = true where n.memberId = :memberId")
  void readAllByMember(@Param("memberId") Long memberId);

  @Modifying
  @Query("update Notification n set n.isSend = true where n.id in :ids")
  void updateAllToSend(@Param("ids") List<Long> ids);

  void deleteAllByMemberId(Long memberId);

  void deleteAllByBookmarkId(Long bookmarkId);
  void deleteAllByBookmarkIdIn(List<Long> bookmarkIds);

  @Modifying
  @Query("delete Notification n where n.memberId = : memberId and n.id in :ids")
  void deleteByMemberAndIds(@Param("memberId") Long memberId, @Param("ids") List<Long> ids);

}

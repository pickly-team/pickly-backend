package org.pickly.service.notification.repository.interfaces;

import org.pickly.service.notification.entity.Notification;
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

  @Query("select n from Notification n where n.isSend = true and n.deletedAt is null")
  List<Notification> findAllByMemberIdAndDeletedAtNull(Long memberId);

  @Query("select n from Notification n where n.isSend = false and n.sendDateTime <= :now" +
      " and n.deletedAt is null")
  List<Notification> getNotificationsToSend(@Param("now") LocalDateTime now);

  @Query("select n from Notification n "
      + "where n.isSend = true and n.deletedAt is null and n.id = :id")
  Optional<Notification> findByIdAndDeletedAtNull(@Param("id") Long id);


  @Modifying
  @Query("update Notification n set n.isSend = true where n.id in :ids")
  void updateAllToSend(@Param("ids") List<Long> ids);

}

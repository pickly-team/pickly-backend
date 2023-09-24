package org.pickly.service.domain.notification.repository.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.notification.entity.Notification;
import org.pickly.service.domain.notification.repository.interfaces.NotificationJdbcRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationJdbcRepositoryImpl implements NotificationJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void saveAll(List<Notification> notifications) {
    jdbcTemplate.batchUpdate("insert into notification" +
            "(member_id, bookmark_id, title, content, is_checked, is_send, send_date_time, notification_type) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setLong(1, notifications.get(i).getMemberId());
            ps.setLong(2, notifications.get(i).getBookmarkId());
            ps.setString(3, notifications.get(i).getTitle());
            ps.setString(4, notifications.get(i).getContent());
            ps.setBoolean(5, notifications.get(i).isChecked());
            ps.setBoolean(6, notifications.get(i).isSend());
            ps.setTimestamp(7, Timestamp.valueOf(notifications.get(i).getSendDateTime()));
            ps.setInt(8, notifications.get(i).getNotificationType().getType());
          }

          @Override
          public int getBatchSize() {
            return notifications.size();
          }
        });
  }

}

package org.pickly.service.domain.category.repository.impl;

import lombok.RequiredArgsConstructor;
import org.pickly.service.domain.category.dto.controller.request.CategoryOrderNumUpdateReq;
import org.pickly.service.domain.category.entity.Category;
import org.pickly.service.domain.category.repository.interfaces.CategoryJdbcRepository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryJdbcRepositoryImpl implements CategoryJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void createCategories(List<Category> categories) {
    jdbcTemplate.batchUpdate("insert into category" +
            "(member_id, name, emoji, order_num) values (?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setLong(1, categories.get(i).getMember().getId());
            ps.setString(2, categories.get(i).getName());
            ps.setString(3, categories.get(i).getEmoji());
            ps.setInt(4, categories.get(i).getOrderNum());
          }

          @Override
          public int getBatchSize() {
            return categories.size();
          }
        });
  }

  @Override
  public void updateCategoryOrderNums(List<CategoryOrderNumUpdateReq> reqs) {
    jdbcTemplate.batchUpdate("update category set order_num = ? where id = ?",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setInt(1, reqs.get(i).getOrderNum());
            ps.setLong(2, reqs.get(i).getCategoryId());
          }

          @Override
          public int getBatchSize() {
            return reqs.size();
          }
        });
  }

}

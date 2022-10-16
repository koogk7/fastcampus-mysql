package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class TimelineRepository {
    static final String TABLE = "Timeline";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Timeline> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Timeline.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .postId(resultSet.getLong("postId"))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();


    public List<Timeline> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, int size) {
        var params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("memberId", memberId)
                .addValue("size", size);

        String query = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId and id < :id
                ORDER BY id DESC
                LIMIT :size
                """, TABLE);

        return namedParameterJdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public List<Timeline> findAllByMemberIdAndOrderByIdDesc(Long memberId, int size) {
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        String query = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY id DESC
                LIMIT :size
                """, TABLE);

        return namedParameterJdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public void bulkInsert(List<Timeline> timelines) {
        var sql = String.format("""
                INSERT INTO `%s` (memberId, postId, createdAt)
                VALUES (:memberId, :postId, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = timelines
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}

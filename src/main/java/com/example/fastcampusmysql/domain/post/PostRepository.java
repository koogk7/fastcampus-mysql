package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.helper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    static final String TABLE = "Post";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Post> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> Post.builder()
            .id(resultSet.getLong("id"))
            .memberId(resultSet.getLong("memberId"))
            .contents(resultSet.getString("contents"))
            .createdDate(resultSet.getObject("createdDate", LocalDate.class))
            .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
            .build();

    public List<Post> findByMemberId(Long memberId) {
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        String query = String.format("SELECT * FROM `%s` WHERE id = :id", TABLE);
        return jdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
        var params = new BeanPropertySqlParameterSource(request);
        String query = String.format("""
                SELECT memberId, createdDate, count(id) as cnt 
                FROM %s 
                WHERE memberId = :memberId and createdDate between :firstDate and :lastDate
                GROUP BY memberId, createdDate
                """, TABLE);

        RowMapper<DailyPostCount> mapper = (ResultSet resultSet, int rowNum) -> new DailyPostCount(
                resultSet.getLong("memberId"),
                resultSet.getObject("createdDate", LocalDate.class),
                resultSet.getLong("cnt")
        );

        return jdbcTemplate.query(query, params, mapper);
    }

    public Page<Post> findAllByMemberId(Long memberId, PageRequest pageRequest) {
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("offset", pageRequest.getOffset())
                .addValue("size", pageRequest.getPageSize());

        Sort sort = pageRequest.getSort();
        String query = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :offset, :size
                """, TABLE, PageHelper.orderBy(sort));

        var posts = jdbcTemplate.query(query, params, ROW_MAPPER);
        return new PageImpl<Post>(posts, pageRequest, getCount(memberId));
    }

    private Integer getCount(Long memberId) {
        String countQuery = String.format("""
                SELECT count(id)
                FROM %s
                WHERE memberId = :memberId
                """, TABLE);
        var countParam = new MapSqlParameterSource().addValue("memberId", memberId);
        return jdbcTemplate.queryForObject(countQuery,  countParam, Integer.class);
    }

    public Post save(Post post) {
        if (post.getId() == null)
            return insert(post);
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다");
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO `%s` (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

}

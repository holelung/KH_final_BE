package com.kh.saintra.anonymous.model.dao;

import com.kh.saintra.anonymous.model.dto.AnonymousDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AnonymousMapper {

    private final JdbcTemplate jdbcTemplate;

    public AnonymousMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AnonymousDto> rowMapper = (rs, rowNum) -> {
        AnonymousDto dto = new AnonymousDto();
        dto.setId(rs.getLong("ID"));
        dto.setUserId(rs.getLong("USER_ID"));
        dto.setTitle(rs.getString("TITLE"));
        dto.setContent(rs.getString("CONTENT"));
        dto.setCreateDate(rs.getDate("CREATE_DATE"));
        dto.setIsActive(rs.getString("IS_ACTIVE"));
        return dto;
    };

    public List<AnonymousDto> findAll() {
        String sql = "SELECT * FROM TB_BOARD_ANONYMOUS WHERE IS_ACTIVE = 'Y' ORDER BY ID DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public AnonymousDto findById(Long id) {
        String sql = "SELECT * FROM TB_BOARD_ANONYMOUS WHERE ID = ? AND IS_ACTIVE = 'Y'";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public int save(AnonymousDto dto) {
        String sql = """
            INSERT INTO TB_BOARD_ANONYMOUS (USER_ID, TITLE, CONTENT, CREATE_DATE, IS_ACTIVE)
            VALUES (?, ?, ?, SYSDATE, 'Y')
        """;
        return jdbcTemplate.update(sql, dto.getUserId(), dto.getTitle(), dto.getContent());
    }


    public int update(AnonymousDto dto) {
        String sql = "UPDATE TB_BOARD_ANONYMOUS SET TITLE = ?, CONTENT = ? WHERE ID = ? AND IS_ACTIVE = 'Y'";
        return jdbcTemplate.update(sql, dto.getTitle(), dto.getContent(), dto.getId());
    }

    public int delete(Long id) {
        String sql = "UPDATE TB_BOARD_ANONYMOUS SET IS_ACTIVE = 'N' WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<AnonymousDto> search(String keyword) {
        String sql = """
            SELECT * FROM TB_BOARD_ANONYMOUS 
            WHERE IS_ACTIVE = 'Y' 
              AND (TITLE LIKE '%' || ? || '%' OR CONTENT LIKE '%' || ? || '%')
            ORDER BY ID DESC
        """;
        return jdbcTemplate.query(sql, rowMapper, keyword, keyword);
    }

    public List<AnonymousDto> searchMyBoard(String keyword, Long userId) {
        String sql = """
            SELECT * FROM TB_BOARD_ANONYMOUS
            WHERE IS_ACTIVE = 'Y'
              AND USER_ID = ?
              AND (TITLE LIKE '%' || ? || '%' OR CONTENT LIKE '%' || ? || '%')
            ORDER BY ID DESC
        """;
        return jdbcTemplate.query(sql, rowMapper, userId, keyword, keyword);
    }
}

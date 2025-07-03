package com.kh.saintra.anonymous.model.dao;

import com.kh.saintra.anonymous.model.dto.CommentDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CommentMapper {

    private final JdbcTemplate jdbcTemplate;

    public CommentMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CommentDto> rowMapper = (rs, rowNum) -> {
        CommentDto dto = new CommentDto();
        dto.setId(rs.getLong("ID"));
        dto.setBoardId(rs.getLong("BOARD_ID"));
        dto.setUserId(rs.getLong("USER_ID"));
        dto.setContent(rs.getString("CONTENT"));
        dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
        dto.setIsActive(rs.getString("IS_ACTIVE"));
        return dto;
    };

    public List<CommentDto> findByBoardId(Long boardId) {
        String sql = "SELECT * FROM TB_COMMENT_ANONYMOUS WHERE BOARD_ID = ? AND IS_ACTIVE = 'Y'";
        return jdbcTemplate.query(sql, rowMapper, boardId);
    }

    public int save(CommentDto dto) {
        String sql = """
            INSERT INTO TB_COMMENT_ANONYMOUS (BOARD_ID, USER_ID, CONTENT, CREATE_DATE, IS_ACTIVE)
            VALUES (?, ?, ?, SYSDATE, 'Y')
        """;
        return jdbcTemplate.update(sql, dto.getBoardId(), dto.getUserId(), dto.getContent());
    }


    public int update(CommentDto dto) {
        String sql = "UPDATE TB_COMMENT_ANONYMOUS SET CONTENT = ? WHERE ID = ? AND IS_ACTIVE = 'Y'";
        return jdbcTemplate.update(sql, dto.getContent(), dto.getId());
    }

    public int delete(Long id) {
        String sql = "UPDATE TB_COMMENT_ANONYMOUS SET IS_ACTIVE = 'N' WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }
}

package com.kh.saintra.anonymous.model.dao;

import com.kh.saintra.anonymous.model.dto.FileDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class FileMapper {

    private final JdbcTemplate jdbcTemplate;

    public FileMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(FileDto dto) {
        String sql = """
            INSERT INTO TB_FILE_ANONYMOUS (ID, BOARD_ID, URL, ORIGIN)
            VALUES (SEQ_FILE_ANONYMOUS.NEXTVAL, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql, dto.getBoardId(), dto.getUrl(), dto.getOrigin());
    }

    public List<FileDto> findByBoardId(Long boardId) {
        String sql = "SELECT ID, BOARD_ID, URL, ORIGIN FROM TB_FILE_ANONYMOUS WHERE BOARD_ID = ?";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            FileDto dto = new FileDto();
            dto.setId(rs.getLong("ID"));
            dto.setBoardId(rs.getLong("BOARD_ID"));
            dto.setUrl(rs.getString("URL"));
            dto.setOrigin(rs.getString("ORIGIN"));
            return dto;
        }, boardId);
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM TB_FILE_ANONYMOUS WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }
}

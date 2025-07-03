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
            INSERT INTO TB_FILE_ANONYMOUS (ID, BOARD_ID, FILE_NAME, ORIGINAL_NAME, FILE_PATH, UPLOAD_DATE, IS_ACTIVE)
            VALUES (SEQ_FILE_ANONYMOUS.NEXTVAL, ?, ?, ?, ?, SYSDATE, 'Y')
        """;
        return jdbcTemplate.update(sql, dto.getBoardId(), dto.getFileName(), dto.getOriginalName(), dto.getFilePath());
    }

    public List<FileDto> findByBoardId(Long boardId) {
        String sql = "SELECT * FROM TB_FILE_ANONYMOUS WHERE BOARD_ID = ? AND IS_ACTIVE = 'Y'";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
            FileDto dto = new FileDto();
            dto.setId(rs.getLong("ID"));
            dto.setBoardId(rs.getLong("BOARD_ID"));
            dto.setFileName(rs.getString("FILE_NAME"));
            dto.setOriginalName(rs.getString("ORIGINAL_NAME"));
            dto.setFilePath(rs.getString("FILE_PATH"));
            dto.setUploadDate(rs.getTimestamp("UPLOAD_DATE"));
            dto.setIsActive(rs.getString("IS_ACTIVE"));
            return dto;
        }, boardId);
    }

    public int deleteById(Long id) {
        String sql = "UPDATE TB_FILE_ANONYMOUS SET IS_ACTIVE = 'N' WHERE ID = ?";
        return jdbcTemplate.update(sql, id);
    }
}

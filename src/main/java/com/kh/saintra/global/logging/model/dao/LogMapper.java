package com.kh.saintra.global.logging.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.dto.LogRequest;
import com.kh.saintra.global.logging.model.vo.Log;
import com.kh.saintra.global.logging.model.vo.LogVo;

@Mapper
public interface LogMapper {
    
    void insertLog(LogDTO log);

    List<LogDTO> selectLogList(LogRequest logRequest); 
    Long selectTotalCount(LogRequest logRequest);
}

package com.kh.saintra.job.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.job.model.dto.JobDTO;

@Mapper
public interface JobMapper {
    
    List<JobDTO> getJobList();
}

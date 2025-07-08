package com.kh.saintra.team.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.team.model.dto.TeamDTO;

@Mapper
public interface TeamMapper {
	
	List<TeamDTO> selectAllTeams();
}

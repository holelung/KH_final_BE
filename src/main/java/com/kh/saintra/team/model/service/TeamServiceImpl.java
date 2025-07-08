package com.kh.saintra.team.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.saintra.team.model.dao.TeamMapper;
import com.kh.saintra.team.model.dto.TeamDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
	
	private final TeamMapper teamMapper;

    @Override
    public List<TeamDTO> getAllTeams() {
        return teamMapper.selectAllTeams();
    }
}

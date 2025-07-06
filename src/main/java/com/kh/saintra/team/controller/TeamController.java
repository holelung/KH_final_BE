package com.kh.saintra.team.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.team.model.dto.TeamDTO;
import com.kh.saintra.team.model.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamDTO>>> getAllTeams() {
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.GET_SUCCESS, teamService.getAllTeams(), "팀 조회에 성공하였습니다."));
    }
}

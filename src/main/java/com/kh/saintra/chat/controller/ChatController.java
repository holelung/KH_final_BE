package com.kh.saintra.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.saintra.chat.model.dto.MessageDTO;
import com.kh.saintra.chat.model.service.ChatService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
		
		private final ChatService chatService;
		
		
		@GetMapping
		public ResponseEntity<ApiResponse<List<MessageDTO>>> findMessagesByRoomId(@RequestParam(name="teamId") String teamId,
																 @RequestParam(name="lastTimeStamp", required = false) String lastTimeStamp){
			log.info("📨 메시지 목록 조회 요청: teamId={}, lastTimeStamp={}", teamId, lastTimeStamp);

			
			List<MessageDTO> messages = chatService.findMessagesByRoomId(teamId, lastTimeStamp);
			
			return ResponseEntity.ok(
					ApiResponse.success(ResponseCode.GET_SUCCESS, messages, "메세지 목록 조회 성공"));
		}
		
	}



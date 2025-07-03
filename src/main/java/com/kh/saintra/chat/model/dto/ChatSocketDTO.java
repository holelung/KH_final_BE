package com.kh.saintra.chat.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatSocketDTO {

	private String messageId;

	private Long sender;

	private String content;

	private String type; 
}

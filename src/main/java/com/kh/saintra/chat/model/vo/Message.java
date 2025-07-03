package com.kh.saintra.chat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	
	private String teamId;

	private String messageId;

	private Long senderId;

	private String content;

}

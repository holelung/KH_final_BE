package com.kh.saintra.chat.model.service;

import java.util.List;

import com.kh.saintra.chat.model.dto.MessageDTO;

public interface ChatService {
	
	List<MessageDTO> findMessagesByRoomId(String teamId, String lastTimeStamp);
	
	MessageDTO sendChatMessage(MessageDTO message);
	
	MessageDTO updateChatMessage(MessageDTO message);
	
	MessageDTO deleteChatMessage(MessageDTO message);

}

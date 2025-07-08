package com.kh.saintra.chat.model.service;

import java.util.List;

import com.kh.saintra.chat.model.dto.MessageDTO;
import com.kh.saintra.team.model.dto.TeamDTO;

public interface ChatService {
	
	List<MessageDTO> findMessagesByRoomId(String teamId, String lastTimeStamp);
	
	MessageDTO sendChatMessage(MessageDTO message);
	
	MessageDTO updateChatMessage(MessageDTO message);
	
	MessageDTO deleteChatMessage(MessageDTO message);
	
	List<TeamDTO> getAllChatRooms();

}

package com.kh.saintra.chat.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.saintra.chat.model.dto.MessageDTO;
import com.kh.saintra.chat.model.vo.GetMessageRequest;
import com.kh.saintra.chat.model.vo.Message;

@Mapper
public interface ChatMapper {
	
    // 메시지 목록 조회 (팀 ID 기준, 최신 20개)
    List<MessageDTO> findMessagesByTeamId(GetMessageRequest request);

    // 메시지 전송 (DB 저장용)
    int sendChatMessage(Message message);

    // 단일 메시지 조회
    MessageDTO findMessageByMessageId(String messageId);

    // 메시지 존재 여부 확인
    boolean checkMessage(String messageId);

    // 해당 유저가 보낸 메시지인지 확인
    boolean checkIsSender(MessageDTO message);

    // 메시지 수정
    int updateChatMessage(MessageDTO message);

    // 메시지 삭제
    int deleteChatMessage(String messageId);

}

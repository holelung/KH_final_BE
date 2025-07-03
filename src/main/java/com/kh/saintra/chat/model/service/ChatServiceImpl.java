package com.kh.saintra.chat.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kh.saintra.chat.model.dao.ChatMapper;
import com.kh.saintra.chat.model.dto.MessageDTO;
import com.kh.saintra.chat.model.vo.GetMessageRequest;
import com.kh.saintra.chat.model.vo.Message;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMapper chatMapper;

    @Override
    public List<MessageDTO> findMessagesByRoomId(String teamId, String lastTimeStamp) {
        GetMessageRequest request = GetMessageRequest.builder()
                .teamId(teamId)
                .lastTimeStamp(lastTimeStamp)
                .build();

        return chatMapper.findMessagesByTeamId(request);
    }

    @Override
    public MessageDTO sendChatMessage(MessageDTO message) {
        MessageDTO response = new MessageDTO();
        response.setSenderId(message.getSenderId());

        String checkContent = message.getContent().replaceAll("\\n", "").trim();
        if (checkContent.isEmpty()) {
            response.setType("빈 메시지는 전송할 수 없습니다.");
            return response;
        }

        String messageId = UUID.randomUUID().toString();
        Message msg = Message.builder()
                .messageId(messageId)
                .teamId(String.valueOf(message.getTeamId()))
                .senderId(message.getSenderId())
                .content(message.getContent())
                .build();

        int result = chatMapper.sendChatMessage(msg);
        if (result == 0) {
            response.setType("메시지 전송에 실패했습니다.");
            return response;
        }

        MessageDTO saved = chatMapper.findMessageByMessageId(messageId);
        saved.setType("send");
        return saved;
    }

    @Override
    public MessageDTO updateChatMessage(MessageDTO message) {
        MessageDTO response = new MessageDTO();
        response.setSenderId(message.getSenderId());

        String content = message.getContent().replaceAll("\\n", "").trim();
        if (content.isEmpty()) {
            response.setType("빈 메시지는 수정할 수 없습니다.");
            return response;
        }

        if (!chatMapper.checkIsSender(message)) {
            response.setType("작성자만 수정할 수 있습니다.");
            return response;
        }

        if (!chatMapper.checkMessage(String.valueOf(message.getMessageId()))) {
            response.setType("메시지가 존재하지 않습니다.");
            return response;
        }

        int updated = chatMapper.updateChatMessage(message);
        if (updated == 0) {
            response.setType("메시지 수정 실패");
            return response;
        }

        response.setMessageId(message.getMessageId());
        response.setContent(message.getContent());
        response.setType("update");
        return response;
    }

    @Override
    public MessageDTO deleteChatMessage(MessageDTO message) {
        MessageDTO response = new MessageDTO();
        response.setSenderId(message.getSenderId());

        if (!chatMapper.checkIsSender(message)) {
            response.setType("작성자만 삭제할 수 있습니다.");
            return response;
        }

        if (!chatMapper.checkMessage(String.valueOf(message.getMessageId()))) {
            response.setType("메시지가 존재하지 않습니다.");
            return response;
        }

        int deleted = chatMapper.deleteChatMessage(String.valueOf(message.getMessageId()));
        if (deleted == 0) {
            response.setType("메시지 삭제 실패");
            return response;
        }

        response.setMessageId(message.getMessageId());
        response.setType("delete");
        return response;
    }
}

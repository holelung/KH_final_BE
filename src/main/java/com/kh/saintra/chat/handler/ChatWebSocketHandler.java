package com.kh.saintra.chat.handler;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.saintra.chat.model.dto.MessageDTO;
import com.kh.saintra.chat.model.service.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 채팅방 ID → 해당 방의 WebSocketSession들
    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    private final ChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = extractRoomId(session);
        log.info("✅ 소켓 연결 요청 도착: roomId = {}", roomId);


        if (roomId == null || roomId.isBlank()) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        rooms.computeIfAbsent(roomId, key -> ConcurrentHashMap.newKeySet()).add(session);
        log.info("🔌 연결됨 | roomId={}, sessionId={}", roomId, session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("💬 메시지 수신: {}", message.getPayload());

        // 메시지 파싱
        MessageDTO msg = objectMapper.readValue(message.getPayload(), MessageDTO.class);
        String roomId = extractRoomId(session);

        // 메시지 처리
        switch (msg.getType()) {
            case "send":
                msg = chatService.sendChatMessage(msg);
                break;
            case "update":
                msg = chatService.updateChatMessage(msg);
                break;
            case "delete":
                msg = chatService.deleteChatMessage(msg);
                break;
            default:
                log.warn("❗ 알 수 없는 메시지 타입: {}", msg.getType());
                return;
        }

        // 모든 세션에 브로드캐스트
        TextMessage outbound = new TextMessage(objectMapper.writeValueAsString(msg));
        for (WebSocketSession user : rooms.getOrDefault(roomId, Collections.emptySet())) {
            if (user.isOpen()) {
                user.sendMessage(outbound);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = extractRoomId(session);
        rooms.getOrDefault(roomId, Collections.emptySet()).remove(session);
        log.info("🔌 연결 종료 | roomId={}, sessionId={}", roomId, session.getId());
    }

    private String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath(); // 예: /ws/chat/room/{roomId}
        String[] segments = path.split("/");
        return segments.length > 0 ? segments[segments.length - 1] : null;
    }
}

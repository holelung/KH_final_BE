package com.kh.saintra.user.controller;


import java.security.Principal;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.user.model.dto.UserStatusDTO;
import com.kh.saintra.user.model.service.UserStatusService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserStatusController {
    
    private final UserStatusService statusService;

    // 클라이언트의 상태 전송 메시지를 받음
    @MessageMapping("/status.update")
    public void updateStatus(UserStatusDTO dto) {
        statusService.updateStatus(dto);
    }

    @MessageMapping("/status.me")
    public void getStatus() {
        statusService.getStatus();
    }

    @EventListener
    public void handleSessionConnected(SessionSubscribeEvent ev) {
       
        Authentication auth = (Authentication) StompHeaderAccessor.wrap(ev.getMessage()).getUser();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        System.out.println("구독한 유저" + user);
        statusService.markOnline(user.getUsername());
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent ev) {
        Principal user = StompHeaderAccessor.wrap(ev.getMessage()).getUser();
        statusService.markOffline(user.getName());
    }
}

package com.kh.saintra.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.kh.saintra.global.config.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer{

    private final WebSocketHandshakeInterceptor handshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        
        registry.enableSimpleBroker("/status", "/chat"); // 클라이언트가 구독할 주소
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 때 접두사

    
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .addInterceptors(handshakeInterceptor)
            .withSockJS(); //WebSocket 연결 엔드포인트
    }
    

}

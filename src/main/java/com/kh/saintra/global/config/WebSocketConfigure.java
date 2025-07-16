package com.kh.saintra.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.kh.saintra.global.config.interceptor.StompAuthAndLoggingInterceptor;
import com.kh.saintra.global.config.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@SuppressWarnings("null")
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer{

    private final WebSocketHandshakeInterceptor handshakeInterceptor;
    private final StompAuthAndLoggingInterceptor stompInterceptor;
    
    @Value("${url.origin-patterns}")
    private String originPatterns;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns(originPatterns)
            .addInterceptors(handshakeInterceptor)
            .withSockJS(); //WebSocket 연결 엔드포인트
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        
        // @MessageMapping 메서드로 라우팅할 prefix
        registry.setApplicationDestinationPrefixes("/app");
        // 브로커(클라이언트-브로드캐스트)용 prefix
        registry.enableSimpleBroker("/topic", "/queue");
    
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        registration.interceptors(stompInterceptor);
    }

}

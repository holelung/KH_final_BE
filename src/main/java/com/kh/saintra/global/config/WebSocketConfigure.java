package com.kh.saintra.global.config;

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
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer{

    private final WebSocketHandshakeInterceptor handshakeInterceptor;
    private final StompAuthAndLoggingInterceptor stompInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
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

        // registration.interceptors(new ChannelInterceptor() {
        //     @Override
        //     public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //         StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //         //StompHeaderAccessor.wrap(message);
        //         System.out.println("▶ preSend command=" + accessor.getCommand() + ", destination="
        //                 + accessor.getDestination()    );
                
        //         assert accessor != null;

        //         // STOMP CONNECT 프레임일 때만 JWT 검사
        //         if (StompCommand.CONNECT.equals(accessor.getCommand())) {
        //             String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        //             if (authHeader != null && authHeader.startsWith("Bearer ")) {
        //                 String token = authHeader.substring(7);
        //                 try {
        //                     // 토큰 파싱 & 검증
        //                     Claims claims = jwtUtil.parseJwt(token);
        //                     String username = claims.getSubject();

        //                     // UserDetailsService로부터 사용자 정보 로드
        //                     CustomUserDetails user = (CustomUserDetails)
        //                         userDetailsService.loadUserByUsername(username);

        //                     // Authentication 객체 생성 & 세팅
        //                     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        //                     SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //                     System.out.println("여기는 토큰검증!"+ authenticationToken);
        //                     System.out.println("프린시펄에 값이 있낭?"+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        //                     accessor.setUser(authenticationToken);
                            
        //                 } catch (ExpiredJwtException e) {
        //                     throw new AuthenticateTimeOutException(ResponseCode.AUTH_FAIL, "만료된 토큰입니다.");
        //                 } catch (JwtException e) {
        //                     throw new AuthenticateFailException(ResponseCode.AUTH_FAIL,
        //                             "유효하지 않은 토큰입니다.");
        //                 }
                        
        //             } else {
        //                 throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "Authorization 헤더(Bearer 토큰)가 없습니다.");
        //             }

        //             Map<String, Object> sessionAttr = accessor.getSessionAttributes();
        //             LogDTO log = (LogDTO)sessionAttr.get("LogInfo");
        //             log.setUserId(((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());

        //         }
        //         return message;
        //     }
        // });
    }

}

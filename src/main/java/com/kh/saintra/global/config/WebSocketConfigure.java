package com.kh.saintra.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.config.interceptor.WebSocketHandshakeInterceptor;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.util.token.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfigure implements WebSocketMessageBrokerConfigurer{

    private final WebSocketHandshakeInterceptor handshakeInterceptor;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


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
        
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                System.out.println("▶ preSend command=" + accessor.getCommand() + ", destination="
                        + accessor.getDestination()    );
                // STOMP CONNECT 프레임일 때만 JWT 검사
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        try {
                            // 토큰 파싱 & 검증
                            Claims claims = jwtUtil.parseJwt(token);
                            String username = claims.getSubject();

                            // UserDetailsService로부터 사용자 정보 로드
                            CustomUserDetails user = (CustomUserDetails)
                                userDetailsService.loadUserByUsername(username);

                            // Authentication 객체 생성 & 세팅
                            UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            accessor.setUser(auth);
                            SecurityContextHolder.getContext().setAuthentication(auth);

                        } catch (ExpiredJwtException e) {
                            throw new AuthenticateTimeOutException(ResponseCode.AUTH_FAIL, "만료된 토큰입니다.");
                        } catch (JwtException e) {
                            throw new AuthenticateFailException(ResponseCode.AUTH_FAIL,
                                    "유효하지 않은 토큰입니다.");
                        }
                    } else {
                        throw new InvalidAccessException(ResponseCode.AUTH_FAIL, "Authorization 헤더(Bearer 토큰)가 없습니다.");
                    }
                }
                return message;
            }
        });
    }

}

package com.kh.saintra.global.config.interceptor;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.AuthenticateFailException;
import com.kh.saintra.global.error.exceptions.AuthenticateTimeOutException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.logging.model.dto.LogDTO;
import com.kh.saintra.global.logging.model.service.LogService;
import com.kh.saintra.global.logging.model.service.LogServiceImpl;
import com.kh.saintra.global.util.token.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompAuthAndLoggingInterceptor implements ChannelInterceptor {

    private final LogService logService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //StompHeaderAccessor.wrap(message);
        System.out.println("▶ preSend command=" + accessor.getCommand() + ", destination="
                + accessor.getDestination()    );
        
        assert accessor != null;

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
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    System.out.println("여기는 토큰검증!"+ authenticationToken);
                    System.out.println("프린시펄에 값이 있낭?"+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());

                    accessor.setUser(authenticationToken);
                    
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


    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent,
            Exception ex) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor != null) {
            Map<String, Object> sessionAttr = accessor.getSessionAttributes();
            LogDTO log = sessionAttr != null ?
                (LogDTO) sessionAttr.get("LogInfo")
                : null;
            if( log != null){
                log.setUserId((
                        (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
                    ).getId());

                log.setActionResult("Success");
                
                if( ex != null) {
                    log.setActionResult("Failure");
                }

                logService.saveLog(log);
                sessionAttr.remove("LogInfo");   
            }
        }
    }
}

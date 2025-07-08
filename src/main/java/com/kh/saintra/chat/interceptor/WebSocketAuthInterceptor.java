package com.kh.saintra.chat.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.kh.saintra.global.util.token.util.JwtUtil;
import com.kh.saintra.user.model.dao.UserMapper;
import com.kh.saintra.user.model.dto.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        String query = request.getURI().getQuery();

        if (query == null || !query.contains("token=")) {
            log.warn("WebSocket 요청 시 token 파라미터 누락");
            return false;
        }

        String token = query.split("token=")[1];

        try {
            Claims claims = jwtUtil.parseJwt(token);
            String username = claims.getSubject();

            // 사용자 정보 조회
            UserDTO user = userMapper.getUserByUsername(username);
            if (user == null) {
                log.warn("존재하지 않는 사용자: {}", username);
                return false;
            }

            if ("N".equals(user.getIsActive())) {
                log.warn("비활성화된 사용자 접근 차단: {}", username);
                return false;
            }

            attributes.put("userId", user.getUsername());
            attributes.put("userNo", user.getId());

            log.info(" WebSocket 인증 성공: userId={}, userNo={}", user.getUsername(), user.getId());
            return true;

        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("JWT 처리 오류: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                                ServerHttpResponse response,
                                WebSocketHandler wsHandler,
                                Exception exception) {
    }
}

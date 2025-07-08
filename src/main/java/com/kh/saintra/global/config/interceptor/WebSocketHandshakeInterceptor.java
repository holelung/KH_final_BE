package com.kh.saintra.global.config.interceptor;

import java.net.http.HttpHeaders;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import com.kh.saintra.global.logging.model.dto.LogDTO;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        
        System.out.println("\n ======================= ");
        System.out.println("요청 URI : " + request.getURI());
        System.out.println("요청 IP : " + request.getRemoteAddress());
        System.out.println("Referer : " + request.getHeaders().getFirst("Referer"));
        System.out.println("요청 Method : " + request.getMethod());
        LogDTO log = LogDTO.builder()
                .actionArea(request.getURI().toString())
                .actionType(request.getMethod().toString())
                .clientIp(request.getRemoteAddress().toString())
                .referer(request.getHeaders().getFirst("Referer"))
                .build();
        
        attributes.put("LogInfo", log);
        return true;
        
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
       // 아무것도 안함
    }
    
}

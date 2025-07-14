package com.kh.saintra.global.config.filter;

import java.io.IOException;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.util.token.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        Claims claims;
        try {
            claims = jwtUtil.parseJwt(token);

        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                "{\"code\":\"TOKEN_EXPIRED\",\"message\":\"토큰이 만료되었습니다.\"}"
            );
            return;
        } catch (JwtException e){
            log.info("유효하지않은 토큰");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("유효하지 않은 토큰입니다.");
            return;
        }

        String type = claims.get("type", String.class);
        String path = request.getServletPath();

        if("/api/auth/refresh".equals(path)){
            if(!"refresh".equals(type)){
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                response.getWriter().write("잘못된 토큰 타입");
                return;
            }
        }else{
            if(!"access".equals(type)) {
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                response.getWriter().write("잘못된 토큰 타입");
                return;
            }
        }

        String username = claims.getSubject();

        CustomUserDetails user =
                (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        filterChain.doFilter(request, response);

        // 보안 이슈

        // 1. 이중검증

        // 2. 시크릿 키가 털렸을 때를 대비해서 시크릿키만 알면 토큰값을 만들 수 있는데 서버에서 한 번 더 검증해서 필터
        // 넘어와도 디비에 없으면 그럼 죽어

        // 3. 얘를 저장해서 막 그런거 모든 기기에서 로그아웃 같은 기능 만들 떄 리프레시 토큰 다 지워서 다른데서도 다 지워지게끔

        // 4. 검증 보안 목적
    }
    
}

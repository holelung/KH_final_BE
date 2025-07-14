package com.kh.saintra.global.util.token.model.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.util.token.model.dao.TokenMapper;
import com.kh.saintra.global.util.token.model.vo.RefreshToken;
import com.kh.saintra.global.util.token.model.vo.Tokens;
import com.kh.saintra.global.util.token.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final JwtUtil jwtUtil;
    private final TokenMapper tokenMapper;

    /**
     * 토큰 생성 매서드
     * @return 생성된 토큰을 Tokens(DTO)자료형에 담아서 반환
     */
    @Override
    public Tokens generateToken(String username, Long id) {

        Map<String, String> tokens = createToken(username);
        saveToken(tokens.get("refreshToken"), id);
        return Tokens.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .build();
    }

    /**
     * 토큰 생성 메서드
     * @param username 유저 아이디
     * @return Map<String, String> 으로 accessToken과 refreshToken 반환.
     */
    private Map<String, String> createToken(String username){

        String accessToken = jwtUtil.getAccessToken(username);
        String refreshToken = jwtUtil.getRefreshToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    /**
     * 토큰 저장 메서드
     * @param refreshToken 리프래시 토큰
     * @param id UserTable PK
     */
    private void saveToken(String refreshToken, Long id){
        long expiryMillis = System.currentTimeMillis() + (3600000L * 24 * 7);
        Instant expiryInstant = Instant.ofEpochMilli(expiryMillis);

        LocalDateTime expiryDateTime = expiryInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        RefreshToken token = RefreshToken.builder()
                .userId(id)
                .token(refreshToken)
                .expiration(expiryDateTime)
                .build();
        log.info("{}", id);
        log.info(refreshToken);
        log.info("{}", expiryDateTime);
        
        tokenMapper.saveToken(token);
    }

    @Override
    public Tokens refreshToken(String refreshToken, String username) {
         if(tokenMapper.getRefreshToken(refreshToken)!= 1){
            throw new InvalidAccessException(ResponseCode.INVALID_TOKEN, "유효한 토큰이 아닙니다. 재로그인이 필요합니다.");
        }

        String newAccessToken = jwtUtil.getAccessToken(username);
        Tokens tokens = Tokens.builder()
                        .refreshToken(refreshToken)
                        .accessToken(newAccessToken)
                        .build();
        return tokens;
    }
    
    
}

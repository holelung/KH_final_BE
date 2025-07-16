package com.kh.saintra.global.util.token.model.service;

import com.kh.saintra.global.util.token.model.vo.Tokens;

public interface TokenService {
    
    /**
     * <pre>
     * 토큰을 생성하는 메서드
     * <pre>
     * @param username 유저아이디(사번)
     * @param id 유저테이블 PK
     * @return Tokens(accessToken, refreshToken)
     */
    Tokens generateToken(String username, Long id);

    /**
     * <pre>
     * 리프레시 토큰 검수 메서드
     * 엑세스 토큰이 유요하지 않을때 리프레시 토큰을 요청해서
     * 리프레시 토큰을 받아서 검증한다.
     * 검증에 성공한다면 새로운 accessToken을 리턴한다.
     * </pre>
     * @param refreshToken 리프레시 토큰, username 유저 ID
     * @return Tokens 새로만든 토큰 세트
     */
    Tokens refreshToken(String refreshToken, String username);
}

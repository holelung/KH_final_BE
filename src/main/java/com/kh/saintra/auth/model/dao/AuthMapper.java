package com.kh.saintra.auth.model.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    /**
     * <pre>
     * 아이디 중복체크 Mapper String Type의 ID(username)을 가지고 DB에서 조회 후 COUNT(*) 형태의 값을
     * 반환한다.
     * 
     * <pre>
     * 
     * @param username String
     * @return int COUNT(*)
     */
    int isIdDuplicate(String username);

    /**
     * <pre>
     * 이메일 중복체크 Mapper String Type의 ID(username)을 가지고 DB에서 조회 후 COUNT(*) 형태의 값을
     * 반환한다.
     * 
     * <pre>
     * 
     * @param email String
     * @return int COUNT(*)
     */
    int isEmailDuplicate(String email);
}

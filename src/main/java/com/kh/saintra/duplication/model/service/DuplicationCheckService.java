package com.kh.saintra.duplication.model.service;

public interface DuplicationCheckService {

    /**
     * <pre>
     * 아이디 중복체크 메서드
     * id값을 가지고 DB로가지고 가서 존재여부를 확인하고
     * 존재할 경우 true, 아이디가 없을 경우 false를 반환한다.
     * </pre>
     * 
     * @param username 유저 아이디
     * @return 아이디 중복(존재함): true, 아이디 중복X: false
     */
    void isIdDuplicate(String username);

    /**
     * <pre>
     * 이메일 중복체크 메서드
     * email값을 가지고 DB로가지고 가서 존재여부를 확인하고
     * 존재할 경우 true, 아이디가 없을 경우 false를 반환한다.
     * </pre>
     * 
     * @param email 유저 이메일
     * @return 중복(존재함): true, 아이디 중복X: false
     */
    void isEmailDuplicate(String email);

}

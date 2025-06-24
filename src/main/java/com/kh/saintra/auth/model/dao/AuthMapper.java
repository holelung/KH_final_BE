package com.kh.saintra.auth.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.vo.User;

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

    /**
     * 가입신청 테이블에 데이터 추가
     * 
     * @param id USER 테이블 PK
     */
    void insertJoin(Long id);

    /**
     * <pre>
     * 가입 승인
     * 유저의 PK를 받아서 IS_APPROVE 컬럼의 값을 'Y'로 변경한다.
     * </pre>
     * @param id USER 테이블 PK
     */
    void approveJoin(Long id);
    
    /**
     * <pre>
     * TB_JOIN 테이블(가입요청)의 행 삭제
     * 가입 승인이 완료된 후 가입요청 테이블을 삭제한다.
     * </pre>
     * @param id USER 테이블 PK
     */
    void deleteJoin(Long id);

    /**
     * <pre>
     * 가입 요청 목록 조회
     * 
     * </pre>
     * 
     * @return List<User>
     */
    List<UserDTO> getApproveList();

}

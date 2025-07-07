package com.kh.saintra.auth.model.service;

import java.util.List;
import java.util.Map;
import com.kh.saintra.auth.model.dto.ApproveRequest;
import com.kh.saintra.auth.model.dto.ChangePasswordDTO;
import com.kh.saintra.auth.model.dto.FindPasswordDTO;
import com.kh.saintra.auth.model.dto.LoginFormDTO;
import com.kh.saintra.auth.model.vo.ApproveUser;
import com.kh.saintra.auth.model.vo.CustomUserDetails;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.vo.User;

public interface AuthService {

    /**
     * <pre>
     * 로그인 요청을 처리하는 메서드
     * 요청값 검증 이후 Token을 생성하여
     * 유저 데이터와 함꼐 Map으로 반환한다.
     * </pre>
     * @param login username(사번), password(비밀번호)
     * @return Map<String, Object> : CustomUserDetails, Tokens
     */
    ApiResponse<Map<String,Object>> login(LoginFormDTO login);

    /**
     * <pre>
     * 로그아웃 요청 처리 메서드
     * TB_TOKEN에 있는 refreshToken의 정보를 제거한다.
     * </pre>
     * 
     * @return void
     */
    ApiResponse<Void> logout();

    // 회원가입 요청 목록 조회
    /**
     * 회원가입 요청 목록 조회
     * 관리자가 회원가입 요청한 사용자의 정보를
     * 확인 할 수 있도록 List를 반환한다.
     * @return List<User>
     */
    ApiResponse<Map<String, Object>> getApproveList(ApproveRequest request);

    /**
     * <pre>
     * 회원가입 승인
     * 회원가입 승인 요청이 오면
     * IS_ACTIVE 컬럼의 값을 변경하고
     * TB_JOIN 테이블의 행을 삭제한다.
     * </pre>
     * 
     * @param userId TB_USER 의 PK
     * @return
     */
    ApiResponse<Void> approveJoin(Long userId);


    /**
     * <pre>
     * 비밀번호 찾기
     * 
     * 비밀번호 찾기 요청으로 매개변수를 받음
     * 1. 존재하는 회원인지 확인
     *  1-2. 회원 테이블에서 저장된 Email을 가져옴
     * 2. 비밀번호 변경URL을 전송하는 MailService 호출
     * </pre>
     * 
     * @param changePasswordInfo 사용자 아이디
     * @return
     */
    ApiResponse<Void> findPassword(String username);
    
    /**
     * <pre>
     * 비밀번호 변경(비밀번호 찾기와 연결됨)
     * 
     * 비로그인 상태로 비밀번호를 변경하기 위해 
     * Key값을 Body에 담아서 비밀번호 변경요청을 보냄
     * 
     * Key를 통해 USER 테이블 PK를 SELECT함
     * PK를 가지고 해당 유저의 비밀번호를 변경함
     * </pre>
     * 
     * @param changePassword 변경할 비밀번호(2번), KEY값
     * @return
     */
    ApiResponse<Void> changePasswordByKey(ChangePasswordDTO changePassword);
    


    /**
     * <pre>
     * SecurityContextHolder의 
     * Context안의 Authentication 안의 principal 에 있는 
     * 유저 정보를 반환하는 메서드
     * </pre>
     * 
     * @return CustomUserDetails
     */
    CustomUserDetails getUserDetails();


    /**
     * 비밀번호 확인 메서드
     * @param password 비밀번호
     * @return 비밀번호가 검증된 유저의 PK
     */
    Long checkPassword(String password);

}

package com.kh.saintra.user.model.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dto.Attendance;
import com.kh.saintra.user.model.dto.AttendanceRequest;
import com.kh.saintra.user.model.dto.ListRequest;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserPasswordDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserCompanyInfoDTO;
import com.kh.saintra.user.model.dto.UserUpdateEmailDTO;


public interface UserService {
    
    /**
     * <pre>
     * 회원가입 
     * 유저 정보를 받아서 회원테이블에 정보를 저장하고
     * 회원 승인을 요청한다.
     * </pre>
     * @param user UserDTO에서 유효성검사를 처리한 데이터임
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> join(UserDTO user);

    /**
     * <pre>
     * 회원 목록 조회
     * 회원의 목록을 조회하는 GET 메서드임
     * </pre>
     * @param userSearch 검색을 하는데 필요한 정보들이 들어있음(검색어, 오름/내림차순, 부서, 직급)
     * @return ApiResponse(ResponseCode code, <T> data, String message)
     */
    ApiResponse<List<UserDTO>> getUserList();

    ApiResponse<Map<String,Object>> getUserListByAdmin(UserSearchDTO userSearch);

    /**
     * <pre>
     * 회원 정보 조회
     * 
     * </pre>
     * @param id USER 테이블 pk
     * @return UserDTO
     */
    ApiResponse<UserDTO> getUser();

    /**
     * <pre>
     * 회원 정보 조회(유저 본인)
     * 
     * 유저가 자신의 정보를 조회하는 요청을 처리하는 메서드
     * </pre>
     * @return ApiResponse(ResponseCode code, <T> data, String message)
     */
    ApiResponse<UserDTO> getUserByMe();

    /**
     * <pre>
     * 회원 정보 수정(관리자) -> 직급/부서/팀/권한 변경
     * 관리자의 회원정보 수정 요청을 처리하는 메서드
     * 직급/부서/팀/권한을 변경할 수 있다.
     * </pre>
     * @param userUpdate 유저이름과 부서, 직급, 팀, 권한이 들어있는 DTO
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> updateUserByAdmin(UserCompanyInfoDTO userUpdate);

    /**
     * <pre>
     * 회원 정보 수정(유저)
     * 유저가 자신의 정보를 변경하는 요청을 처리하는 메서드
     * 해당 요청에서 유저의 이름/전화번호/주소를 변경할 수 있다
     * </pre>
     * @param userProfile 유저의 아이디,이름,전화번호,주소를 받아서 전달하는 DTO
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> updateUser(UserProfileDTO userProfile);
    // 회원 프로필 사진 변경
    /**
     * <pre>
     * 회원 프로필 사진 변경
     * 이미지를 받아서 FileService에서 파일 처리완료후
     * 회원 Table에 이미지를 저장
     * </pre>
     * @param file 회원 프로필 사진 이미지
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> updateUserProfileImage(MultipartFile file);

    /**
     * <pre>
     * 비밀번호 변경(로그인)
     * 
     * 마이페이지에서 비밀번호 변경
     * 1. AuthContext에서 로그인된 사용자 정보 가져옴
     * 2. 현재 비밀번호가 일치하는지 확인
     * 3. 현재 비밀번호와 새 비밀번호가 다른지 확인
     * 4. 비밀 번호 변경
     * 
     * </pre>
     * 
     * @param UserPasswordDTO 현재 비밀번호, 변경할 비밀번호
     * @return
     */
    ApiResponse<Void> changePassword(UserPasswordDTO userPassword);
    
    /**
     * <pre>
     * 이메일 변경 
     * 이메일 변경시 인증을 한번 해가지고 이메일 변경을 처리한다.
     * 앞에서 이메일 인증을 하고 들어온다고 가정함
     * </pre>
     * @param email EmailDTO에는 Valid처리를 한 String타입의 변수가 들어있음
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> updateEmail(UserUpdateEmailDTO userEmail);

    /** 
     * <pre>회원 탈퇴(유저 본인)</pre>
     * 
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> deleteUser();
    
    /** 
     * <pre>회원 탈퇴(관리자 제어)</pre>
     * @param id 관리자가 탈퇴시킬 유저의 ID
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> deleteUserByAdmin(Long id);
    
    /**
     * <pre>
     * 근태 조회(유저용)
     * 조회하는 유저 아이디를 받아서 
     * 근태 목록을 반환함
     * </pre>
     * 
     * @param attendance 근태조회 기간(startDate, endDate)가 포함됨 
     * @return ApiResponse(ResponseCode code, <T> data, String message)
     */
    ApiResponse<List<Attendance>> getAttendance(AttendanceRequest attendance);

    /**
     * 근태 조회 관리자용
     * 
     * @param attendance 근태조회 기간(startDate, endDate), 조회하는 유저ID가 포함됨
     * @return ApiResponse(ResponseCode code, <T> data, String message)
     */
    ApiResponse<List<Attendance>> getAttendanceByAdmin(AttendanceRequest attendance);


    /**
     * <pre>출근 처리</pre>
     * @return ApiResponse(ResponseCode code, String message)
     */
    ApiResponse<Void> checkIn();

    /**
     * <pre>퇴근 처리 </pre>
     * @return ApiResponse(ResponseCode code, String message)
     * */ 
    ApiResponse<Void> checkOut();

    
}

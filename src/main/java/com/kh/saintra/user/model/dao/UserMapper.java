package com.kh.saintra.user.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.Attendance;
import com.kh.saintra.user.model.dto.AttendanceRequest;
import com.kh.saintra.user.model.dto.ListRequest;
import com.kh.saintra.user.model.dto.UserCompanyInfoDTO;
import com.kh.saintra.user.model.dto.UserUpdateEmailDTO;
import com.kh.saintra.user.model.vo.UpdateEmail;
import com.kh.saintra.user.model.vo.UpdatePassword;
import com.kh.saintra.user.model.vo.User;

@Mapper
public interface UserMapper {

    /**
     * <pre>
     * 회원가입 정보 INSERT
     * </pre>
     * 
     * @param user 회원가입 유저 정보
     * @return INSERT 결과(int)
     */
    int join(User user);

    /**
     * <pre>
     * 회원정보 가져오기
     * 회원정보중 IS_ACTIVE 컬럼의 값이 'Y'인 행의 데이터를 불러옴
     * </pre>
     * @param username
     * @return
     */
    UserDTO getUserByUsername(String username);

    /**
     * <pre>
     * 회원정보 가져오기
     * 회원 ID(PK)를 가지고 정보를 가지고온다.
     * </pre>
     * @param id
     * @return UserDTO
     */
    UserDTO getUserByUserId(Long id);
    
    /**
     * <pre>
     * 회원정보 가져오기
     * 회원정보중 IS_ACTIVE 컬럼의 값이 'N'인 행의 데이터를 불러옴
     * </pre>
     * @param username USER PK
     * @return UserDTO
     */
    UserDTO getUserByUsernameForApprove(String username);

    /**
     * <pre>
     * 회원 목록 불러오기
     * </pre>
     * @param userSearch 검색어, 검색기간
     * @return List<User>
     */
    List<UserDTO> getUserList(UserSearchDTO userSearch);
    
    List<UserDTO> getUserListByAdmin(UserSearchDTO request);

    Long getUserListTotalCount(UserSearchDTO request);


    /**
     * <pre>
     * 회원 개인정보 업데이트
     * 
     * 회원 이름, 주소, 전화번호를 변경한다.
     * 
     * </pre>
     * @param userProfile
     */
    void updateUser(UserProfileDTO userProfile);

    /**
     * 이메일 업데이트
     * @param userEmail
     */
    void updateEmail(UpdateEmail updateEmail);

    /**
     * 비밀번호 변경
     * @param updatePassword id와 password가 들어있다.
     */
    void updatePassword(UpdatePassword updatePassword);

    /**
     * 회원 탈퇴
     * (유저본인)
     * @param id pk
     */
    void deleteUser(Long id);


    /**
     * 회원정보 변경(관리자)
     * 
     * 부서, 직급, 팀, 권한을 변경함
     * @param user
     */
    void updateUserByAdmin(UserCompanyInfoDTO user);


    /**
     * 근태 조회
     * 
     * @param attendance id와 조회기간(시작날짜, 끝나는 날짜)
     * @return List<근태정보>
     */
    List<Attendance> getAttendance(AttendanceRequest attendance);

    /**
     * 마지막 출퇴근 기록 조회
     * 
     * 가장 최근의 출퇴근 INSERT 기록을 가지고온다,
     * @param id pk
     * @return Attendance(출퇴근 기록)
     */
    Attendance checkLatestAttendance(Long id);

    void checkIn(Long id);

    void checkOut(Long id);

    UserDTO getUserByMe(Long id);
}

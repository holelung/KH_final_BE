package com.kh.saintra.user.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.kh.saintra.auth.model.dao.AuthMapper;
import com.kh.saintra.auth.model.service.AuthService;
import com.kh.saintra.department.model.dao.DepartmentMapper;
import com.kh.saintra.duplication.model.service.DuplicationCheckService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.InvalidAccessException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.job.model.dao.JobMapper;
import com.kh.saintra.team.model.dao.TeamMapper;
import com.kh.saintra.user.model.dao.UserMapper;
import com.kh.saintra.user.model.dto.Attendance;
import com.kh.saintra.user.model.dto.AttendanceRequest;
import com.kh.saintra.user.model.dto.ListRequest;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserPasswordDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserCompanyInfoDTO;
import com.kh.saintra.user.model.dto.UserUpdateEmailDTO;
import com.kh.saintra.user.model.vo.UpdateEmail;
import com.kh.saintra.user.model.vo.UpdatePassword;
import com.kh.saintra.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DuplicationCheckService duplicationService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final AuthService authService;
    private final TeamMapper teamMapper;
    private final DepartmentMapper departmentMapper;
    private final JobMapper jobMapper;

    @Override
    @Transactional
    public ApiResponse<Void> join(UserDTO user) {
        // 아이디 중복검사
        duplicationService.isIdDuplicate(user.getUsername());
        // 이메일 중복검사
        duplicationService.isEmailDuplicate(user.getEmail());

        User userValue = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .realname(user.getRealname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address1(user.getAddress1())
                .address2(user.getAddress2())
                .ssn(user.getSsn())
                .build();
        
        try {
            userMapper.join(userValue);
        } catch (Exception e) {
            e.getStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원가입 정보 입력 실패");
        }
        
        Long userId = getUserByUsernameForApprove(userValue.getUsername()).getId();

        // 가입신청 테이블 삽입
        try {
            authMapper.insertJoin(userId);
        } catch (Exception e) {
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "가입신청 실패..");
        }
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS, "회원가입 요청 성공");
    }

    @Override
    @Transactional
    public ApiResponse<UserDTO> getUser() {

        Long id = authService.getUserDetails().getId();

        UserDTO result = userMapper.getUserByUserId(id);

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "유저 정보 조회 성공");
    }

    @Override
    public ApiResponse<List<UserDTO>> getUserList() {
        
        // 부서 확인
        // if(!authService.getUserDetails().getDeptId().equals("1")){
        //     throw new InvalidAccessException(ResponseCode.INVALID_ACCESS, "접근 권한이 없습니다.");
        // }

        List<UserDTO> result = userMapper.getUserList();

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "유저 목록 조회 성공");
    }

    

    @Override
    public ApiResponse<Map<String, Object>> getUserListByAdmin(UserSearchDTO request) {
        request.setCurrentPage( request.getCurrentPage() * request.getRowsPerPage());

        Map<String, Object> result = new HashMap<>();

        try {
            result.put("list", userMapper.getUserListByAdmin(request));
            result.put("total", userMapper.getUserListTotalCount(request));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "유저목록 조회 실패");
        }

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "유저목록 조회 성공");
    }


    @Override
    @Transactional
    public ApiResponse<Void> updateUserByAdmin(UserCompanyInfoDTO companyInfo) {
        // 부서 확인
        // if(!authService.getUserDetails().getDeptId().equals("1")){
        // throw new InvalidAccessException(ResponseCode.INVALID_ACCESS, "접근 권한이 없습니다.");
        // }

        if(userIsExistById(companyInfo.getId()) == null){
            throw new InvalidAccessException(ResponseCode.INVALID_VALUE, "없는 사용자 입니다.");
        }

        try {
            userMapper.updateUserByAdmin(companyInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "사원정보 수정 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "사원정보 수정완료");
    }

    @Override
    @Transactional
    public ApiResponse<Void> updateUser(UserProfileDTO userProfile) {
        
        userProfile.setId(authService.getUserDetails().getId());
        try {
            userMapper.updateUser(userProfile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원 정보 업데이트 실패");
        }
        
        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "회원 정보 업데이트 성공");
    }

    @Override
    public ApiResponse<Void> updateUserProfileImage(MultipartFile file) {
        // 파일서비스 구현 후 구현 예정
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfileImage'");
    }

    @Override
    @Transactional
    public ApiResponse<Void> changePassword(UserPasswordDTO userPassword) {
        
        // 비밀번호 확인
        Long id = authService.checkPassword(userPassword.getPrevPassword());

        UpdatePassword updatePassword = UpdatePassword.builder()
                .id(id)
                .password(passwordEncoder.encode(userPassword.getPassword()))
                .build();
        
        try {
            userMapper.updatePassword(updatePassword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "비밀번호 변경 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "비밀번호 변경 성공");
    }



    @Override
    @Transactional
    public ApiResponse<Void> updateEmail(UserUpdateEmailDTO userEmail) {
        
        duplicationService.isEmailDuplicate(userEmail.getEmail());

        Long id = authService.getUserDetails().getId();

        UpdateEmail updateEmail = UpdateEmail.builder()
                .id(id)
                .email(userEmail.getEmail())
                .build();
        
        try {
            userMapper.updateEmail(updateEmail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "이메일 변경 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "이메일 변경 성공");
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteUser() {
        
        Long id = authService.getUserDetails().getId();

        try {
            userMapper.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원탈퇴 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "회원 탈퇴 성공");
    }

    @Override
    @Transactional
    public ApiResponse<Void> deleteUserByAdmin(Long id) {
        
        if(userIsExistById(id) == null){
            throw new InvalidAccessException(ResponseCode.INVALID_VALUE, "없는 사용자 입니다.");
        }

        try {
            userMapper.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원 강제 탈퇴 실패");
        }

        return ApiResponse.success(ResponseCode.UPDATE_SUCCESS, "회원 강제 탈퇴 성공");
    }

    @Override
    @Transactional
    public ApiResponse<List<Attendance>> getAttendance(AttendanceRequest attendance) {
        List<Attendance> result = null;

        attendance.setId(authService.getUserDetails().getId());

        try {
            result = userMapper.getAttendance(attendance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "근태 조회 실패");
        }

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "근태조회 성공");
    }

    

    @Override
    @Transactional
    public ApiResponse<List<Attendance>> getAttendanceByAdmin(AttendanceRequest attendance) {
        List<Attendance> result = null;

        if(userIsExistById(attendance.getId()) == null){
            throw new InvalidAccessException(ResponseCode.INVALID_VALUE, "없는 사용자 입니다.");
        }

        try {
            result = userMapper.getAttendance(attendance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "근태 조회 실패");
        }

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "근태조회 성공");
    }


    // 출근
    @Override
    @Transactional
    public ApiResponse<Void> checkIn() {
        Long id = authService.getUserDetails().getId();

        Attendance data = null;

        try {
            data = userMapper.checkLatestAttendance(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "근태 조회 실패");
        }

        if(data != null && data.getType().equals("Attendance")){
            throw new InvalidAccessException(ResponseCode.INVALID_ACCESS, "이미 출근했습니다.");
        }
        
        try {
            userMapper.checkIn(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "출근 실패");
        }

        return ApiResponse.success(ResponseCode.INSERT_SUCCESS, "출근 처리되었습니다.");
    }

    // 퇴근
    @Override
    @Transactional
    public ApiResponse<Void> checkOut() {
        
        Long id = authService.getUserDetails().getId();

        try {
            userMapper.checkOut(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "퇴근 실패");
        }
        
        return ApiResponse.success(ResponseCode.INSERT_SUCCESS, "퇴근 처리 완료");
    }

    @Transactional(
        propagation = Propagation.REQUIRED,
        readOnly = true
    )
    private UserDTO getUserByUsernameForApprove(String username){
        return userMapper.getUserByUsernameForApprove(username);
    }

    @Transactional(
        propagation = Propagation.REQUIRED
    )
    private UserDTO userIsExistById(Long id){
        UserDTO user = null;

        try {
            user = userMapper.getUserByUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "유저 정보 가져오기 실패");
        }

        return user;
    }


    @Override
    public ApiResponse<UserDTO> getUserByMe() {
        Long id = authService.getUserDetails().getId();

        try {
            UserDTO user = userMapper.getUserByMe(id);
            return ApiResponse.success(ResponseCode.GET_SUCCESS, user, "유저 정보 조회 성공");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "유저 정보 조회 실패");
        }
        
    }


    @Override
    @Transactional
    public ApiResponse<Map<String, Object>> getCompanyInfo() {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("team", teamMapper.selectAllTeams());
            result.put("dept", departmentMapper.selectDepartmentList());
            result.put("job", jobMapper.getJobList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "부서정보 조회 실패");
        }

        return ApiResponse.success(ResponseCode.GET_SUCCESS, result, "회사 부서정보 조회성공");
    }


    
}

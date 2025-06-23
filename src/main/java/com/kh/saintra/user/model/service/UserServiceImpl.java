package com.kh.saintra.user.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.saintra.auth.model.dto.EmailDTO;
import com.kh.saintra.auth.util.DuplicationCheckService;
import com.kh.saintra.global.enums.ResponseCode;
import com.kh.saintra.global.error.exceptions.DatabaseOperationException;
import com.kh.saintra.global.error.exceptions.DuplicateDataException;
import com.kh.saintra.global.response.ApiResponse;
import com.kh.saintra.user.model.dao.UserMapper;
import com.kh.saintra.user.model.dto.AttendanceDTO;
import com.kh.saintra.user.model.dto.UserDTO;
import com.kh.saintra.user.model.dto.UserProfileDTO;
import com.kh.saintra.user.model.dto.UserSearchDTO;
import com.kh.saintra.user.model.dto.UserUpdateDTO;
import com.kh.saintra.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DuplicationCheckService duplicationService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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
            throw new DatabaseOperationException(ResponseCode.SQL_ERROR, "회원가입 정보 입력 실패"+e.getStackTrace());
        }

        return ApiResponse.success(ResponseCode.INSERT_SUCCESS, "회원가입 요청 성공");
    }

    @Override
    public ApiResponse<Object> getUserList(UserSearchDTO userSearch) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserList'");
    }

    @Override
    public ApiResponse<Void> updateUserByAdmin(UserUpdateDTO userUpdate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserByAdmin'");
    }

    @Override
    public ApiResponse<Void> updateUser(UserProfileDTO userProfile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public ApiResponse<Void> updateUserProfileImage(MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUserProfileImage'");
    }

    @Override
    public ApiResponse<Void> updateEmail(EmailDTO email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEmail'");
    }

    @Override
    public ApiResponse<Void> deleteUser(String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public ApiResponse<Void> deleteUserByAdmin(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUserByAdmin'");
    }

    @Override
    public ApiResponse<Object> getAttendance(AttendanceDTO attendance) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAttendance'");
    }

    @Override
    public ApiResponse<Void> checkIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkIn'");
    }

    @Override
    public ApiResponse<Void> checkOut() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkOut'");
    }
}

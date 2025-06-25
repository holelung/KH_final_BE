package com.kh.saintra.user.model.dao;

import org.apache.ibatis.annotations.Mapper;
import com.kh.saintra.user.model.dto.UserDTO;
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
     * 회원정보중 IS_ACTIVE 컬럼의 값이 'N'인 행의 데이터를 불러옴
     * </pre>
     * @param username USER PK
     * @return UserDTO
     */
    UserDTO getUserByUsernameForApprove(String username);
}

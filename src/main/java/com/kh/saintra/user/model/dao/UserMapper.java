package com.kh.saintra.user.model.dao;

import org.apache.ibatis.annotations.Mapper;

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
}

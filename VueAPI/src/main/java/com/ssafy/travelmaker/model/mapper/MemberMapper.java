package com.ssafy.travelmaker.model.mapper;

import com.ssafy.travelmaker.model.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface MemberMapper {

    MemberDto login(MemberDto memberDto) throws SQLException;

    MemberDto userInfo(String userid) throws SQLException;

    void saveRefreshToken(Map<String, String> map) throws SQLException;

    Object getRefreshToken(String userid) throws SQLException;

    void deleteRefreshToken(Map<String, String> map) throws SQLException;

    int register(MemberDto memberDto) throws SQLException;
    
    void deleteMember(String userid) throws SQLException;
    
    boolean updateMember(MemberDto memberDto) throws SQLException;
}

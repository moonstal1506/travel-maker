package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.exception.TripException;
import com.ssafy.travelmaker.model.MemberDto;
import com.ssafy.travelmaker.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final SqlSession sqlSession;

    @Override
    public boolean isDuplicated(String userid) throws Exception {
        return userInfo(userid) != null;
    }

    @Override
    public boolean register(MemberDto memberDto) throws Exception {
        if (isDuplicated(memberDto.getUserid())) {
            throw new TripException("이미 등록된 아이디입니다.");
        }
        return sqlSession.getMapper(MemberMapper.class).register(memberDto) == 1;
    }

    @Override
    public MemberDto login(MemberDto memberDto) throws Exception {
        if (memberDto.getUserid() == null || memberDto.getUserpwd() == null)
            return null;
        return sqlSession.getMapper(MemberMapper.class).login(memberDto);
    }

    @Override
    public MemberDto userInfo(String userid) throws Exception {
        return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
    }

    @Override
    public void saveRefreshToken(String userid, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("token", refreshToken);
        sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String userid) throws Exception {
        return sqlSession.getMapper(MemberMapper.class).getRefreshToken(userid);
    }

    @Override
    public void deleRefreshToken(String userid) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        map.put("token", null);
        sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
    }

    @Override
    public void deleteMember(String userid) throws Exception {
        sqlSession.getMapper(MemberMapper.class).deleteMember(userid);
    }
    
    @Override
    public boolean updateMember(MemberDto memberDto) throws Exception {
        return sqlSession.getMapper(MemberMapper.class).updateMember(memberDto); // 업데이트 성공 시 true 반환
    }
}

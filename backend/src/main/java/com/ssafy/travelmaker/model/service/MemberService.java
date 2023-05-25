package com.ssafy.travelmaker.model.service;

import com.ssafy.travelmaker.model.MemberDto;

public interface MemberService {
	public boolean isDuplicated(String userid) throws Exception;
	public boolean register(MemberDto memberDto) throws Exception;
	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	public void deleteMember(String userid) throws Exception;
	public boolean updateMember(MemberDto memberDto) throws Exception;
}

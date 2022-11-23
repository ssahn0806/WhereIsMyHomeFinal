package com.ssafy.house.model.service;

import java.util.Map;

import com.ssafy.house.model.dto.MemberDto;


public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;
	
	boolean register(MemberDto member) throws Exception;

	boolean modify(MemberDto member) throws Exception;

	boolean delete(String userid) throws Exception;
	boolean favorite(Map<String, Object> info) throws Exception;
	
	
}

package com.ssafy.house.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dto.MemberDto;
import com.ssafy.house.model.mapper.MemberMapper;



@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

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
	public boolean register(MemberDto member) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).register(member);
	}
	
	@Override
	public boolean modify(MemberDto member) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).modify(member);
	}

	@Override
	public boolean delete(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).delete(userid);
	}

	@Override
	public boolean favorite(Map<String, Object> info) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).registerFavorite(info);
	}

}

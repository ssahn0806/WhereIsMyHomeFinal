package com.ssafy.house.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.MemberDto;


@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public boolean register(MemberDto member) throws SQLException;
	public boolean modify(MemberDto member) throws SQLException;
	public boolean delete(String userid) throws SQLException;
	public boolean registerFavorite(Map<String, Object> info) throws SQLException;
	
}

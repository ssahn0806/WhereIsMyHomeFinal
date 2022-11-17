package com.ssafy.house.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.User;

@Mapper
public interface UserDAO {

	String login(Map<String,Object> info);

	int register(User user);

	int modify(User user);

	int delete(String userid);

	User getUser(String userid);

	String findPassword(Map<String,Object> info);

	int registerFavorite(Map<String,Object> info);

}
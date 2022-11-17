package com.ssafy.house.model.service;

import java.util.Map;

import com.ssafy.house.model.dto.User;

public interface UserService {

	String login(Map<String,Object> info);

	boolean register(User user);

	boolean modify(User user);

	boolean delete(String userid);

	User getUser(String userid);

	boolean favorite(Map<String,Object> info);

	String findPassword(Map<String,Object> info);

}
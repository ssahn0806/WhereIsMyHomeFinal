package com.ssafy.house.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.UserDAO;
import com.ssafy.house.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Override
	public String login(Map<String,Object> info) {
		return userDao.login(info);
	}
	
	@Override
	public boolean register(User user) {
		return userDao.register(user) > 0;
	}
	
	@Override
	public boolean modify(User user) {
		return userDao.modify(user) > 0;
	}

	@Override
	public boolean delete(String userid) {
		return userDao.delete(userid) > 0;
	}
	
	@Override
	public User getUser(String userid) {
		return userDao.getUser(userid);
	}
	
	//dongCode,userid
	@Override
	public boolean favorite(Map<String,Object> info) {
		return userDao.registerFavorite(info)>0;
	}
	
	//userid,username
	@Override
	public String findPassword(Map<String,Object> info){
		return userDao.findPassword(info);
	}
	
}

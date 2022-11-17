package com.ssafy.house.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.model.dao.PlaceDAO;
import com.ssafy.house.model.dto.Place;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
	private PlaceDAO placeDao;
	
	@Override
	public List<Place> selectPlaces(Map<String,Object> conditions) {
		return placeDao.selectPlaces(conditions);
	}
	
	@Override
	public int countPlaces(Map<String,Object> conditions) {
		return placeDao.countPlaces(conditions);
	}
}	

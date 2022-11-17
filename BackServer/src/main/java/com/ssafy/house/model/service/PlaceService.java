package com.ssafy.house.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.model.dto.Place;

public interface PlaceService {

	List<Place> selectPlaces(Map<String,Object> conditions);

	int countPlaces(Map<String,Object> conditions);

}
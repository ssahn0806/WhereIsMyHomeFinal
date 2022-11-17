package com.ssafy.house.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.Place;

@Mapper
public interface PlaceDAO {

	List<Place> selectPlaces(Map<String,Object> conditions);

	int countPlaces(Map<String,Object> conditions);

}
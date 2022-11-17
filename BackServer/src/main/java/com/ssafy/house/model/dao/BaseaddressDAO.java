package com.ssafy.house.model.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.BaseAddress;

@Mapper
public interface BaseaddressDAO {

	// 1.시도명,구군명,동명으로 동코드 정보 반환
	BaseAddress selectBaseaddress(Map<String, Object> codes);

	BaseAddress selectFullName(String dongCode);

	// 3.동코드로부터 lat,lng 반환
	Map<String, String> selectLatLng(String dongCode);

}
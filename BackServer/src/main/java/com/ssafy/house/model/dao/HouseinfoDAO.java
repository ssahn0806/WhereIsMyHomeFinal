package com.ssafy.house.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.HouseInfo;

@Mapper
public interface HouseinfoDAO {

	//1.아파트 코드명으로부터 아파트 정보 조회
	HouseInfo selectHouse(int aptCode);

}
package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.HouseDeal;

@Mapper
public interface HousedealDAO {

	// 0.거래내역 No로 거래 상세내역 조회
	HouseDeal selectHouseDealByNo(int no);

	// 1.동코드로부터 거래내역 조회
	List<HouseDeal> selectHouseDealsByDongCode(String dongCode);

	// 2.아파트명으로부터 거래내역 조회
	List<HouseDeal> selectHouseDealsByAptName(String aptName);

}
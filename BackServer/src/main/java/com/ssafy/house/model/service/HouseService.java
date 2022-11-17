package com.ssafy.house.model.service;

import java.util.List;

import com.ssafy.house.model.dto.HouseDeal;
import com.ssafy.house.model.dto.HouseInfo;

public interface HouseService {

	//houseDeal
	//0.거래내역 No로 거래 상세내역 조회
	HouseDeal selectHouseDealByNo(int no);

	//1.동코드로부터 거래내역 조회
	List<HouseDeal> selectHouseDealsByDongCode(String dongCode);

	//2.아파트명으로부터 거래내역 조회
	List<HouseDeal> selectHouseDealsByAptName(String aptName);

	//houseInfo
	//1.아파트 코드명으로부터 아파트 정보 조회
	HouseInfo selectHouse(int aptCode);

	// 거래내역 리스트를 Merge Sort
	List<HouseDeal> mergeSortHouseDeals(List<HouseDeal> houseDeals);

}
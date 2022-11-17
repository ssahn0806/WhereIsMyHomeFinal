package com.ssafy.house.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.house.model.dto.BaseAddress;
import com.ssafy.house.model.dto.DongCode;
import com.ssafy.house.model.dto.GugunCode;
import com.ssafy.house.model.dto.SidoCode;

public interface AddressService {

	//1.시도 코드로 부터 시도지역명 조회
	SidoCode selectSidoCode(String code);

	//2.모든 시도 내역 조회
	List<SidoCode> selectSidoCodes();

	//1.시도 코드로부터 일치하는 구군내역 조회
	List<GugunCode> selectGugunCodes(String sidoCode);

	//1.군구 코드로 부터 동코드 목록 조회
	List<DongCode> selectDongCodes(String gugunCode);

	//1.시도명,구군명,동명으로 동코드 정보 반환
	BaseAddress selectBaseaddress(Map<String, Object> codes);

	//2.동코드로부터 시군구명 풀네임 반환
	BaseAddress selectFullName(String dongCode);

	//3.동코드로부터 lat,lng 반환
	Map<String, String> selectLatLng(String dongCode);

}
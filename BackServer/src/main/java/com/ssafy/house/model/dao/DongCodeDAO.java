package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.DongCode;

@Mapper
public interface DongCodeDAO {

	// 1.군구 코드로 부터 동코드 목록 조회
	List<DongCode> selectDongCodes(String gugunCode);

}
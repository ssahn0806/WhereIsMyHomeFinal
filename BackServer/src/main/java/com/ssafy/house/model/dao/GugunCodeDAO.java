package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.GugunCode;

@Mapper
public interface GugunCodeDAO {

	// 1.시도 코드로부터 일치하는 구군내역 조회
	List<GugunCode> selectGugunCodes(String sidoCode);

}
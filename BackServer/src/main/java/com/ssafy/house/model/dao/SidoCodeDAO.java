package com.ssafy.house.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.model.dto.SidoCode;

@Mapper
public interface SidoCodeDAO {

	//1.시도 코드로 부터 시도지역명 조회
	SidoCode selectSidoCode(String code);

	//2.모든 시도 내역 조회
	List<SidoCode> selectSidoCodes();

}